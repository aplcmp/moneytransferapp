package com.thecompany.moneytransfer;

import com.thecompany.Datastore;

import com.thecompany.moneytransfer.converters.CurrencyAmountConverter;
import com.thecompany.moneytransfer.dos.Account;
import com.thecompany.moneytransfer.dos.User;
import com.thecompany.moneytransfer.messages.CurrencyAmount;
import com.thecompany.moneytransfer.messages.CurrencyTransferRequest;
import com.thecompany.moneytransfer.messages.CurrencyTransferResponse;
import com.thecompany.moneytransfer.messages.CurrencyTransferResult;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AppIT {
    private static Logger logger = LoggerFactory.getLogger(AppIT.class);
    private static final int port = 9099;

    private TTransport transport;
    private static Datastore datastore;

    private int reqSeqId=0;

    @BeforeClass
    public static void beforeClass() throws TTransportException {
        datastore = new Datastore("data.xml");
    }

    @Before
    public void before() throws TTransportException {
        transport = new TSocket("localhost", port);
        transport.open();
    }

    @After
    public void after() {
        transport.close();
    }

    @Test
    public void testSingleRequest() throws TException {
        TProtocol protocol = new  TBinaryProtocol(transport);
        MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);
        requestTransfer(client);
    }

    @Test
    public void testConsequentRequests() throws TException {
        TProtocol protocol = new  TBinaryProtocol(transport);
        MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);

        for (int i=0; i < 1000; ++i)
            requestTransfer(client);

    }

    @Test
    public void testConcurrentMalformedRequests() throws TException, InterruptedException {

        List<CurrencyTransferRequest> requests = new ArrayList<>();
        for (int i=0; i < 1000; ++i) {
            CurrencyTransferRequest req = new CurrencyTransferRequest();
            req.setAmount(new CurrencyAmount(2000L, (short) 50));
            requests.add(req);
        }
        requestTransfersConcurrently(requests);
    }

    @Test
    public void testConcurrentRequestsFromGenerousUser() throws TException, InterruptedException {
        BigDecimal ca11balanceBefore;
        {
            TProtocol protocol = new  TBinaryProtocol(transport);
            MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);
            CurrencyAmount ca11 = client.getBalance("11");
            ca11balanceBefore = CurrencyAmountConverter.to(ca11);
        }

        final int count = 1000;
        CurrencyAmount portion = CurrencyAmountConverter.from(ca11balanceBefore.divide(new BigDecimal(count)));

        // prepare set of requests
        List<CurrencyTransferRequest> requests = new ArrayList<>();
        for (int i=0; i < count; ++i) {
            CurrencyTransferRequest req = new CurrencyTransferRequest();
            req.setSourceAccountId("11");
            req.setTargetAccountId("21");
            req.setAmount(portion);
            req.setTransferReason("Charity for someone");
            requests.add(req);
        }
        requestTransfersConcurrently(requests);

        {
            // check that initial balance was exactly same as is current plus transferred money
            TProtocol protocol = new  TBinaryProtocol(transport);
            MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);
            CurrencyAmount ca11 = client.getBalance("11");
            BigDecimal ca11balanceAfter = CurrencyAmountConverter.to(ca11);
            BigDecimal bdPortion = CurrencyAmountConverter.to(portion);
            Assert.assertEquals(0, ca11balanceAfter.add(bdPortion.multiply(new BigDecimal(count))).compareTo(ca11balanceBefore));
        }
    }

    @Test
    public void testConcurrentRequestsArbitrary() throws TException, InterruptedException {
        List<String> accounts = datastore.getAccountMap().keySet().stream().collect(Collectors.toList());
        int accountsCount = accounts.size();

        final int count = 10*1000;

        // prepare set of requests
        List<CurrencyTransferRequest> requests = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            CurrencyTransferRequest req = new CurrencyTransferRequest();
            req.setSourceAccountId(accounts.get(new Random().nextInt(accountsCount)));
            req.setTargetAccountId(accounts.get(new Random().nextInt(accountsCount)));
            req.setAmount(new CurrencyAmount(new Random().nextInt(10), (short) new Random().nextInt(100)));
            req.setTransferReason("Everyone shares to anyone");
            requests.add(req);
        }

        requestTransfersConcurrently(requests);

    }

    private void requestTransfersConcurrently(List<CurrencyTransferRequest> requests) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int i=0; i < requests.size(); ++i) {

            final int reqId = i;
            exec.execute((Runnable) () -> {

                try {
                    TTransport transport;
                    transport = new TSocket("localhost", port);
                    transport.open();

                    TProtocol protocol = new TBinaryProtocol(transport);
                    MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);

                    logger.info("Issuing request: " + reqId);
                    CurrencyTransferResponse response = client.transfer(requests.get(reqId));
                    logger.info("request {} result: {}, desc: {}", reqId, response.getStatus(), response.getDescription() );
                } catch (TException e) {
                    Assert.fail(e.getMessage());
                }
            });
        }

        exec.shutdown();

        while (!exec.isTerminated()) {
            if (!exec.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.info("Not finished yet");
            }
        }
    }

    // generous user gives money to everyone
    @Test
    public void testSeqCharity() throws TException {
        TProtocol protocol = new  TBinaryProtocol(transport);
        MoneyTransfer.Client client = new MoneyTransfer.Client(protocol);

        Optional<User> opt  = datastore.getUserMap().values().stream().findFirst();
        Assert.assertNotNull(opt.get());
        User generous = opt.get();

        final List<Account> otherAccounts = datastore.getUserMap().values().stream().
                filter(user -> !user.getId().equals(generous.getId())).
                flatMap(user1 -> user1.getAccounts().stream()).collect(Collectors.toList());

        final long charityValue = 10;

        generous.getAccounts().stream().forEach(acc -> {
            try {
                CurrencyAmount ca = client.getBalance(acc.getId());
                Iterator<Account> it = otherAccounts.iterator();
                while (ca.getValue() >= charityValue) {
                    if (!it.hasNext())
                        it = otherAccounts.iterator();

                    Account anotherAcc = it.next();
                    CurrencyTransferRequest req =
                            new CurrencyTransferRequest(acc.getId(), anotherAcc.getId(), "to anybody",
                                    new CurrencyAmount(charityValue, (short) 0), "public charity");
                    logger.info("Issuing request: {}", ++reqSeqId);
                    CurrencyTransferResponse response = client.transfer(req);
                    logger.info("request {} result: {}, desc: {}", reqSeqId, response.getStatus(), response.getDescription() );
                    Assert.assertEquals(response.getDescription(), CurrencyTransferResult.OK, response.getStatus());
                    ca = client.getBalance(acc.getId());
                }
                logger.info("Given away all the funds from account {}: left only {}", acc.getId(), ca);
            } catch (TException e) {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        });

        final User trgUser = datastore.getUserMap().values().stream().filter(user -> !user.getId().equals(generous.getId())).findFirst().get();
        final String trgAcc = trgUser.getAccounts().get(0).getId();
        CurrencyTransferRequest req = new CurrencyTransferRequest(generous.getAccounts().get(0).getId(),
                trgAcc, trgUser.getName(),
                new CurrencyAmount(10, (short) 0), "last transfer");
        CurrencyTransferResponse response = client.transfer(req);
        logger.info("request {} result: {}, desc: {}", req, response.getStatus(), response.getDescription() );
        Assert.assertEquals(response.getDescription(), CurrencyTransferResult.FAIL, response.getStatus());
    }

    private static void requestTransfer(MoneyTransfer.Client client) throws TException
    {
        CurrencyTransferRequest req = new CurrencyTransferRequest();
        req.setAmount(new CurrencyAmount(2000L, (short) 50));
        CurrencyTransferResponse response = client.transfer(req);
        logger.info("request result: {}, desc: {}", response.getStatus(), response.getDescription() );
    }
}
