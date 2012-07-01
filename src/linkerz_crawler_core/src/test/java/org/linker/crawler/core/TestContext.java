package org.linker.crawler.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * The Class TestContext.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:26 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class TestContext implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void test() {
        assertEquals(context != null, true);
    }
}
