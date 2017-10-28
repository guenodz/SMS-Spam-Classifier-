/**
 * Copyright Guendouz Mohamed 2013
 */
package com.guendouz.smsspam;

import com.guendouz.smsspam.classifiers.BaseClassifier;

/**
 * @author Guendouz Mohamed
 */
public class Main {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        /**
         * initialize the base classifier with the default classifier ( Naive
         * Bayes)
         */
        BaseClassifier baseClassifier = new BaseClassifier();
        baseClassifier.train();

        //Test
        String newSMS = "Where do you need to go to get it?";
        System.out.println(newSMS + " is: " + baseClassifier.classifyMessage(newSMS));

    }

}
