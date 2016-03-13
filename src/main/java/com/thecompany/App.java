package com.thecompany;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.thecompany.moneytransfer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App runs HTTP server serving user's requests for:
 * - getting the balance of the account
 * - transferring money from one account to another
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static final String initData = "data.xml";
    private static final int port = 9099;

    private final MoneyTransferHandler handler;
    private final MoneyTransfer.Processor processor;
    private final Datastore datastore;

    public App() {
        datastore = new Datastore(initData);
        handler = new MoneyTransferHandler(datastore);
        processor = new MoneyTransfer.Processor(handler);
    }

    public void start() {
        new Thread(() -> process(processor)).start();
    }

    private void process(MoneyTransfer.Processor processor) {
        final TServerTransport serverTransport;
        final TServer server;
        logger.info("Starting the server...");
        try {
            serverTransport = new TServerSocket(port);
        } catch (Exception e) {
            logger.error("Server caught: ", e);
            return;
        }

        server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        logger.info("Ready to service...");
        server.serve();
    }

    public static void main(String [] args) {
        try {
            App app = new App();
            app.start();
        } catch (Exception e) {
            logger.error("Caught exception: ", e);
        }
    }



}
