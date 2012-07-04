/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:36 PM
 */
public class Main {

    public static void main(String[] args) {
        //Start Spring container.
        ApplicationContext context = new GenericXmlApplicationContext("application.xml");
    }

}
