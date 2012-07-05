/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.callback;

import java.io.Serializable;

/**
 * The Class JobCallBack.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:56 PM
 */
public interface CallBack<E> extends Serializable {

    void onSuccess(E e);

    void onFailed(Exception e);
}
