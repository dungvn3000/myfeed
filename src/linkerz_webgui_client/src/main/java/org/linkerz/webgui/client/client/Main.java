/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.webgui.client.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/31/12, 7:37 PM
 */
public class Main implements EntryPoint {

    @Override
    public void onModuleLoad() {

        Button btn = new Button("Hello");
        btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert("Hello");
            }
        });

        RootPanel.get("loading_panel").setVisible(false);

        RootPanel.get().add(btn);

    }
}
