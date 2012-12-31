package org.linkerz.parser.helper;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * The Class ParserHelper.
 *
 * @author Nguyen Duc Dung
 * @since 12/31/12 6:22 PM
 */
public class ParserHelper extends JFrame {

    private JPanel contentPanel = new JPanel(new MigLayout("inset 20", "[40][grow]"));
    private JButton startBtn = new JButton("Start");
    private JTextField urlTxt = new JTextField();
    private JTextField selectionTxt = new JTextField();
    private JTextField removeText1 = new JTextField();
    private JTextField removeText2 = new JTextField();
    private JTextField removeText3 = new JTextField();
    private JTextArea resultTxt = new JTextArea();
    private JScrollPane scrollingArea = new JScrollPane(resultTxt);
    private JLabel statusLbl = new JLabel("Status: ");


    public ParserHelper() {
        initComponent();
    }

    public void initComponent() {
        setTitle("Parser Helper");
        contentPanel.add(new JLabel("Url: "));
        contentPanel.add(urlTxt, "wrap, grow");
        contentPanel.add(new JLabel("Content selection: "));
        contentPanel.add(selectionTxt, "wrap, grow");
        contentPanel.add(new JLabel("Remove selection: "));
        contentPanel.add(removeText1, "wrap, grow");
        contentPanel.add(new JLabel("Remove selection: "));
        contentPanel.add(removeText2, "wrap, grow");
        contentPanel.add(new JLabel("Remove selection: "));
        contentPanel.add(removeText3, "wrap, grow");
        contentPanel.add(startBtn, "skip 1, wrap");

        contentPanel.add(scrollingArea, "span, grow, h 90%, wrap");
        contentPanel.add(statusLbl, "span, growx");

        setContentPane(contentPanel);
    }

}
