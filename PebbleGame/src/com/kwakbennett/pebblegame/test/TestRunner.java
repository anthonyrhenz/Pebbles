package com.kwakbennett.pebblegame.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BagTest.class, ConfiguratorTest.class, MainTest.class);//put all test classes here

        //get all failures and print to console
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
