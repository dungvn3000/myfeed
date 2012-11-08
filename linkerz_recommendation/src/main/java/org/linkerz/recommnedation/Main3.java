package org.linkerz.recommnedation;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

/**
 * The Class Main3.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 2:32 PM
 */
public class Main3 {

    public static void main(String[] args) {

        double[] x = new double[]{1.0, 0.0, 1.0, 1.0};
        double[] y = new double[]{0.0, 1.0, 1.0, 1.0};

        double  r1 = new Covariance().covariance(x, y);

        System.out.println(r1);

        double r2 = new PearsonsCorrelation().correlation(x, y);

        System.out.println(r2);

        double r3 = new SpearmansCorrelation().correlation(x, y);

        System.out.println(r3);
    }

}
