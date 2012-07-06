/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.callback;

/**
 * The Class CallBackable.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 3:31 PM
 */
public interface CallBackable<R> {

    void setCallBack(CallBack<R> callBack);

}
