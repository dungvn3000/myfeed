/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser.controller;

import org.linkerz.crawler.core.parser.Parser;

import java.util.Map;

/**
 * The Class DefaultParserController.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 4:29 PM
 */
public class DefaultParserController implements ParserController {

    protected Map<String, Parser> parsers;

    @Override
    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    @Override
    public Parser get(String url) {
        return parsers.get("*");
    }
}
