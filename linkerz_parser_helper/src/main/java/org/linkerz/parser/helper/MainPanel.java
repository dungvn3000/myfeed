package org.linkerz.parser.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class MainPanel.
 *
 * @author Nguyen Duc Dung
 * @since 12/31/12 6:07 PM
 */
public class MainPanel extends JPanel {

    private JButton contentSelectionBtn = new JButton("Content Selection Helper");
    private JButton parserHelperBtn = new JButton("Parser Helper");

    public MainPanel() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new GridLayout());
        add(contentSelectionBtn);
        add(parserHelperBtn);

        contentSelectionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContentSelectionHelper contentSelectionHelper = new ContentSelectionHelper();
                contentSelectionHelper.setLocationRelativeTo(null);
                contentSelectionHelper.setExtendedState(JFrame.MAXIMIZED_BOTH);
                contentSelectionHelper.setVisible(true);
            }
        });
    }

}
