/**
 * Copyright Guendouz Mohamed 2013
 */
package com.guendouz.smsspam.classifiers;

import weka.classifiers.lazy.IBk;

/**
 * @author Guendouz Mohamed
 * 
 */
public class KnnClassifier {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/** initialize the Knn Classifier with k = 3 */
		IBk knn = new IBk(3);
		BaseClassifier classifier = new BaseClassifier();
		classifier.setClassifier(knn);
		classifier.train();
		System.err.println(classifier
				.classifyMessage("Where do you need to go to get it?"));

	}

}
