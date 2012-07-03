package org.linkerz.crawler.core.job;

import java.io.Serializable;

/**
 * The Class Job.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:11 AM
 */
public interface Job extends Serializable {
    void execute();
}
