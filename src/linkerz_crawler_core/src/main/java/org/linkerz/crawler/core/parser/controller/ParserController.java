/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser.controller;

import org.linkerz.crawler.core.parser.Parser;

import java.util.Map;

/**
 * The Class ParserController.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 4:24 PM
 */
public interface ParserController {

    /**
     * Set map of parsers for controller.
     *
     * @param parsers for each parser will be used for correct website.
     */
    void setParsers(Map<String, Parser> parsers);


    /**
     * Return a parser for the url.
     * @param url
     * @return
     */
    Parser get(String url);
}
