package org.linkerz.parser.helper;

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
        add(new MainPanel());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 100);
        setVisible(true);
    }
}
