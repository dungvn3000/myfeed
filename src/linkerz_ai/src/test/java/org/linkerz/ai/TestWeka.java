/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.ai;

import com.google.common.io.Resources;
import org.junit.Test;
import org.springframework.util.Assert;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static weka.core.converters.ConverterUtils.DataSource;

/**
 * The Class TestWeka.
 *
 * @author Nguyen Duc Dung
 * @since 8/6/12, 2:53 PM
 */
public class TestWeka extends Assert {

    @Test
    public void testBasicWeka() throws Exception {
        // load data
        File trainFile = new File(Resources.getResource("weather.arff").toURI());
        InputStream trainInputStream = new FileInputStream(trainFile);
        Instances train = DataSource.read(trainInputStream);
        train.setClassIndex(train.numAttributes() - 1);

        File testFile = new File(Resources.getResource("weather_test.arff").toURI());
        InputStream testInputStream = new FileInputStream(testFile);
        Instances test = DataSource.read(testInputStream);
        test.setClassIndex(test.numAttributes() - 1);
        if (!train.equalHeaders(test))
            throw new IllegalArgumentException(
                    "Train and test set are not compatible!");

        // train classifier
        J48 cls = new J48();
        cls.buildClassifier(train);

        // output predictions
        for (int i = 0; i < test.numInstances(); i++) {
            double pred = cls.classifyInstance(test.instance(i));
            System.out.print("predict " + test.classAttribute().value((int) pred));
        }

        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(cls, test);
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }

    @Test
    public void testNaiveBayes() throws Exception {
        // load data
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(Resources.getResource("weather.arff").toURI()));
        Instances train = loader.getStructure();
        train.setClassIndex(train.numAttributes() - 1);

        // train NaiveBayes
        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(train);
        Instance current;
        while ((current = loader.getNextInstance(train)) != null)
            nb.updateClassifier(current);

        // output generated model
        System.out.println(nb);

        File testFile = new File(Resources.getResource("weather_test.arff").toURI());
        InputStream testInputStream = new FileInputStream(testFile);
        Instances test = DataSource.read(testInputStream);
        test.setClassIndex(test.numAttributes() - 1);
        if (!train.equalHeaders(test))
            throw new IllegalArgumentException(
                    "Train and test set are not compatible!");

        for (int i = 0; i < test.numInstances(); i++) {
            double pred = nb.classifyInstance(test.instance(i));
            System.out.print("predict " + test.classAttribute().value((int) pred));
        }
        System.out.println();

        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(nb, test);
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }

    @Test
    public void testCreateNewInstance() throws Exception {
        FastVector outlookValue = new FastVector(3);
        outlookValue.addElement("sunny");
        outlookValue.addElement("overcast");
        outlookValue.addElement("rainy");
        Attribute outlook = new Attribute("outlook", outlookValue);

        Attribute temperature = new Attribute("temperature");

        Attribute humidity = new Attribute("humidity");

        FastVector windyValue = new FastVector(2);
        windyValue.addElement("true");
        windyValue.addElement("false");
        Attribute windy = new Attribute("windy", windyValue);

        FastVector playValue = new FastVector(2);
        playValue.addElement("yes");
        playValue.addElement("no");
        Attribute play = new Attribute("play", playValue);

        FastVector attInfo = new FastVector(6);
        attInfo.addElement(outlook);
        attInfo.addElement(temperature);
        attInfo.addElement(humidity);
        attInfo.addElement(windy);
        attInfo.addElement(play);

        Instances train = new Instances("weather", attInfo, 10);

        Instance instance = new Instance(5);
        instance.setValue(outlook, "sunny");
        instance.setValue(temperature, 85);
        instance.setValue(humidity, 85);
        instance.setValue(windy, "false");
        instance.setValue(play, "no");

        train.add(instance);
        train.setClassIndex(train.numAttributes() - 1);
        // train classifier
        J48 cls = new J48();
        cls.buildClassifier(train);

        Instances test = new Instances("test", attInfo, 10);
        Instance testInstance = new Instance(5);
        instance.setValue(outlook, "sunny");
        instance.setValue(temperature, 85);
        instance.setValue(humidity, 85);
        instance.setValue(windy, "false");
//        Predict value
//        instance.setValue(play, "no");
        test.add(testInstance);
        test.setClassIndex(test.numAttributes() - 1);


        for (int i = 0; i < test.numInstances(); i++) {
            double pred = cls.classifyInstance(test.instance(i));
            System.out.println(pred);
            System.out.print("predict " + test.classAttribute().value((int) pred));
        }

    }

}
