/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.webgui.client.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import org.spiffyui.client.widgets.button.SimpleButton;

/**
 * The Class LinkerZEntryPoint.
 *
 * @author Nguyen Duc Dung
 * @since 7/31/12, 7:37 PM
 */
public class LinkerZEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final SimpleButton btn = new SimpleButton("Hello");
        btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                btn.setInProgress(true);
            }
        });

        RootPanel.get("loading_panel").setVisible(false);
        RootPanel.get().add(btn);
    }
}
