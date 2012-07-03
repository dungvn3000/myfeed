/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 10:40 PM
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("crawlerContext.xml");
    }
}
