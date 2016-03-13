package com.thecompany.moneytransfer;

import com.thecompany.Datastore;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

/**
 *
 */
public class DatastoreTest {

    @Test
    public void testLoad() {
        Datastore datastore = new Datastore("data.xml");
        Assert.assertEquals(2, datastore.getUserMap().size());
        Assert.assertEquals(6, datastore.getAccountMap().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testWrongLoadDupUserId() {
        Datastore datastore = new Datastore("wrong_data_dup_userid.xml");
    }
    @Test(expected = IllegalStateException.class)
    public void testWrongLoadDupAccId() {
        Datastore datastore = new Datastore("wrong_data_dup_accid.xml");
    }
    @Test(expected = IllegalStateException.class)
    public void testWrongLoadDupCcy() {
        Datastore datastore = new Datastore("wrong_data_dup_ccy.xml");
    }

    @Test
    public void testRates() {
        Datastore datastore = new Datastore("data.xml");
        Map<String, Integer> ccys = datastore.getConverter().getCcys();
    }


}
