/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.config;

/**
 * The Class Configurable.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 12:05 AM
 */
public interface Configurable<C extends Config> {

    void setConfig(C config);

}
