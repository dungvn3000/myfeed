/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.session;

import java.lang.annotation.*;

/**
 * The Class RunSesson.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 4:04 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RunInSession {

    Class<? extends Session> sessionClass();

}
