package org.linkerz.parser.tester;

import javax.swing.*;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 12:17 AM
 */
public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        setTitle("LinkerZ Parser Tester Tool");
        add(new ParserPanel());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
