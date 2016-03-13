package com.thecompany;

import com.thecompany.moneytransfer.converters.CurrencyRatesConverter;
import com.thecompany.moneytransfer.dos.Account;
import com.thecompany.moneytransfer.dos.InitialData;
import com.thecompany.moneytransfer.dos.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Datastore is populated from the static file.
 * List of userMap/accountMap is not modifiable
 *
 *
 */
public class Datastore {
    private static Logger logger = LoggerFactory.getLogger(Datastore.class);

    // initial source location
    private final String source;
    // user map
    private final Map<String, User> userMap;
    // account map
    private final Map<String, Account> accountMap;
    // currency converter with rates
    private final CurrencyRatesConverter converter;

    public Datastore(String source) {
        this.source = source;
        logger.info("Loading static data...");
        InitialData initialData;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InitialData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            initialData = (InitialData) jaxbUnmarshaller.unmarshal(ClassLoader.getSystemResourceAsStream(source));
        } catch (JAXBException e) {
            throw new RuntimeException("Cannot load initial data: ", e);
        }

        userMap = Collections.unmodifiableMap(
            initialData.getUserList().stream().collect(
                Collectors.toMap(User::getId, e->e,
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate user id %s", u)); }, HashMap::new)));

        accountMap = Collections.unmodifiableMap(
                userMap.values().stream().
                        flatMap(s->s.getAccounts().stream()).collect(
                                Collectors.toMap(Account::getId, e->e,
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate account id %s", u)); }, HashMap::new)));

        converter = new CurrencyRatesConverter(initialData.getRates());
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public CurrencyRatesConverter getConverter() {
        return converter;
    }

}


