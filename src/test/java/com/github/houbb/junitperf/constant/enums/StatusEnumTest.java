package com.github.houbb.junitperf.constant.enums;

import org.junit.Assert;
import org.junit.Test;

/**
* StatusEnum Tester.
*
* @author author
* @since 2018-01-15 13:30:57.461
* @version 1.0
*/
public class StatusEnumTest {

    /**
    *
    * Method: StatusEnum(cases)
    */
    @Test
    public void StatusEnumTest() throws Exception {
    }

    /**
    *
    * Method: getStatus()
    */
    @Test
    public void getStatusTest() throws Exception {
        Assert.assertEquals("PASSED", StatusEnum.PASSED.getStatus());
        Assert.assertEquals("FAILED", StatusEnum.FAILED.getStatus());
    }


}
