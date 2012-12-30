package org.linkerz.parser.helper;

import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class ParserPanel.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 12:26 AM
 */
public class ParserPanel extends JPanel implements ActionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JButton startBtn = new JButton("Start");
    private JTextField urlTxt = new JTextField();
    private JTextField selectionTxt = new JTextField();
    private JTextArea urlList = new JTextArea();
    private JScrollPane scrollingArea = new JScrollPane(urlList);
    private JLabel statusLbl = new JLabel("Status: ");
    private SimpleCrawler crawler = new SimpleCrawler();

    public ParserPanel() {
        initComponent();
    }

    public void initComponent() {
        setLayout(new MigLayout("inset 20", "[40][grow]", "[][][][fill, grow][]"));
        add(new JLabel("Url:"));
        add(urlTxt, "grow, wrap");
        add(new JLabel("Content Selection:"));
        add(selectionTxt, "grow, wrap");
        add(startBtn, "skip 1, wrap, w 20");
        add(scrollingArea, "span, grow, wrap");
        add(statusLbl, "span, growx");
        urlList.setEditable(false);

        startBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Begin testing");
        urlList.setText("");
        startBtn.setEnabled(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    crawler.crawl(urlTxt.getText(), selectionTxt.getText(), statusLbl, urlList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    startBtn.setEnabled(true);
                }
            }
        });
        thread.start();
    }
}
