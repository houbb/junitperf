package com.github.houbb.junitperf.util;

import com.github.houbb.heaven.util.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

/**
* TimeUtil Tester.
*
* @author author
* @since 2018-01-15 13:30:57.480
* @version 1.0
*/
public class TimeUtilTest {

    /**
    *
    * Method: convertMsToNs(ms)
    */
    @Test
    public void conertMsToNsTest() throws Exception {
        long ms = 1000;
        Assert.assertEquals(1000_000_000, DateUtil.convertMsToNs(ms));
    }


}
