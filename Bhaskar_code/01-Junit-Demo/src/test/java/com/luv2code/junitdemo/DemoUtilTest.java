package com.luv2code.junitdemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemoUtilTest {

    @Test
    public void checkEqualsAndNotEquals(){

        DemoUtils utils =new DemoUtils();

        assertEquals(6,utils.add(2,4),"is valid");
        assertNotEquals(6,utils.add(1,9),"Invalid addition");


    }

    @Test
    public void checkNullAndNotNotNulls(){

        DemoUtils utils =new DemoUtils();
        String str1=null;
        String str2="bhaskar";
        assertNull(utils.checkNull(str1));
        assertNotNull(utils.checkNull(str2));
    }
}
