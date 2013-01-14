package org.linkerz.parser.helper;

import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ParserHelper.
 *
 * @author Nguyen Duc Dung
 * @since 12/31/12 6:22 PM
 */
public class ParserHelper extends JFrame implements ActionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JPanel contentPanel = new JPanel(new MigLayout("inset 20", "[40][grow]"));
    private JButton startBtn = new JButton("Start");
    private JTextField urlTxt = new JTextField();
    private JTextField selectionTxt = new JTextField();
    private JTextField removeText1 = new JTextField();
    private JTextField removeText2 = new JTextField();
    private JTextField removeText3 = new JTextField();
    private JCheckBox showTitleCb = new JCheckBox("Show Title");
    private JCheckBox showDescriptionCb = new JCheckBox("Show Description");
    private JCheckBox showTextCb = new JCheckBox("Show Text");
    private JCheckBox showImageCb = new JCheckBox("Show Image");
    private JTextArea resultTxt = new JTextArea();
    private JScrollPane scrollingArea = new JScrollPane(resultTxt);
    private JLabel statusLbl = new JLabel("Status: ");
    private SimpleCrawler crawler = new SimpleCrawler();

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
        contentPanel.add(new JLabel("Options: "));
        contentPanel.add(showTitleCb, "split 4");
        contentPanel.add(showDescriptionCb);
        contentPanel.add(showTextCb);
        contentPanel.add(showImageCb, "wrap, grow");
        contentPanel.add(startBtn, "skip 1, wrap");

        contentPanel.add(scrollingArea, "span, grow, h 90%, wrap");
        contentPanel.add(statusLbl, "span, growx");

        resultTxt.setEditable(false);

        setContentPane(contentPanel);

        startBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Begin testing");
        startBtn.setEnabled(false);
        resultTxt.setText("");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> removeSelections = new ArrayList<String>();
                    removeSelections.add(removeText1.getText());
                    removeSelections.add(removeText2.getText());
                    removeSelections.add(removeText3.getText());
                    Configuration configuration = new Configuration();
                    configuration.setShowDescription(showDescriptionCb.isSelected());
                    configuration.setShowText(showTextCb.isSelected());
                    configuration.setShowTitle(showTitleCb.isSelected());
                    configuration.setShowImage(showImageCb.isSelected());
                    crawler.crawl(urlTxt.getText(), selectionTxt.getText(), removeSelections, statusLbl, resultTxt, configuration);
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
