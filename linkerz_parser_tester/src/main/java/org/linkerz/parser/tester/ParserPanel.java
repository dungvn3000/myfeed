package org.linkerz.parser.tester;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * The Class ParserPanel.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 12:26 AM
 */
public class ParserPanel extends JPanel {

    private JButton startBtn = new JButton("Start");
    private JTextField urlTxt = new JTextField();
    private JTextField selectionTxt = new JTextField();
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList<String> urlList = new JList<String>(listModel);

    public ParserPanel() {
        initComponent();
    }

    public void initComponent() {
        setLayout(new MigLayout("inset 20", "[40][grow]", "[][][][fill, grow]"));
        add(new JLabel("Url:"));
        add(urlTxt, "grow, wrap");
        add(new JLabel("Content Selection:"));
        add(selectionTxt, "grow, wrap");
        add(startBtn, "skip 1, wrap, w 20");
        add(urlList, "span, grow");
    }
}
