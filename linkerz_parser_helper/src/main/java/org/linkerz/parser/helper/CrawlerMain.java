package org.linkerz.parser.helper;

import javax.swing.*;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 12:17 AM
 */
public class CrawlerMain {
    public static void main(String[] args) {
        ParserHelper parserHelper = new ParserHelper();
        parserHelper.setLocationRelativeTo(null);
        parserHelper.setSize(1000, 500);
        parserHelper.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        parserHelper.setExtendedState(JFrame.MAXIMIZED_BOTH);
        parserHelper.setVisible(true);
    }
}
