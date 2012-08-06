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
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
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
        System.out.println("# - actual - predicted - error - distribution");
        for (int i = 0; i < test.numInstances(); i++) {
            double pred = cls.classifyInstance(test.instance(i));
            double[] dist = cls.distributionForInstance(test.instance(i));
            System.out.print((i + 1));
            System.out.print(" - ");
            System.out.print(test.instance(i).toString(test.classIndex()));
            System.out.print(" - ");
            System.out.print(test.classAttribute().value((int) pred));
            System.out.print(" - ");
            if (pred != test.instance(i).classValue())
                System.out.print("yes");
            else
                System.out.print("no");
            System.out.print(" - ");
            System.out.print(Utils.arrayToString(dist));
            System.out.println();
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

}
