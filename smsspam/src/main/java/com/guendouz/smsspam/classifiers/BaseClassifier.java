/**
 * Copyright Guendouz Mohamed 2013
 */
package com.guendouz.smsspam.classifiers;

import com.guendouz.smsspam.util.DatasetUtil;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * @author Guendouz Mohamed
 * 
 *         BaseClassifier a Base Classifier , this class contain data and
 *         methods to build a classifier. if the classifier is not set than we
 *         use by default the NaiveBayes Classifier.
 * 
 */
public class BaseClassifier {

	/** Instances object to handle the dataset content */
	private Instances train;
	/** Instances object to handle the dataset content after using a Filter */
	private Instances trainFiltered;
	/** the current classifier */
	private Classifier classifier;
	/** Evaluation object to evaluate the currrent classifier */
	private Evaluation evaluation;
	/** the weka TF*IDF vector generator */
	private StringToWordVector wordVector;
	/** use this to tokenize text as words */
	private WordTokenizer tokenizer;

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	public BaseClassifier() {

		try {
			/** loading the arff file content */
			train = DatasetUtil.loadArff("dataset/dataset.arff");
			/** initializing the filter */
			wordVector = new StringToWordVector();
			wordVector.setInputFormat(train);
			tokenizer = new WordTokenizer();
			wordVector.setTokenizer(tokenizer);
			/** generating the TF*IDF Vector */
			trainFiltered = Filter.useFilter(train, wordVector);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * make the Instance weka object from a String
	 * 
	 * @param sms
	 *            the String to be converted
	 * 
	 * @return Instance object
	 */

	private Instance makeInstance(String sms) {
		Instance instance = new Instance(2);
		Attribute attribute = train.attribute("Message");
		instance.setValue(attribute, attribute.addStringValue(sms));
		instance.setDataset(train);
		return instance;

	}

	/**
	 * classify a new message (ham or spam)
	 * 
	 * @param message
	 *            a text message to be classified
	 * 
	 * @return a class label (spam or ham )
	 */

	public String classifyMessage(String message) throws Exception {
		String result = "";
		Instance instance = makeInstance(message);
		wordVector.input(instance);
		Instance filteredInstance = wordVector.output();

		double predicted = classifier.classifyInstance(filteredInstance);
		result = train.classAttribute().value((int) predicted);
		return result;

	}

	/**
	 * train the current classifier with the Training data and print the
	 * evaluation results to the screen
	 */
	public void train() throws Exception {
		if (classifier == null)
			classifier = new NaiveBayes();

		/** build the classifier */
		classifier.buildClassifier(trainFiltered);
		/** Initialize the evaluation by the training data (test) */
		evaluation = new Evaluation(trainFiltered);
		/** evaluate the current classifier */
		evaluation.evaluateModel(classifier, trainFiltered);
		System.out.println(evaluation.toSummaryString());

	}

}
