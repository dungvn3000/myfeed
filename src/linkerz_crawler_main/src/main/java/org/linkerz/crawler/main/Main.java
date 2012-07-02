package org.linkerz.crawler.main;

import org.linkerz.crawler.core.controller.Controller;
import org.linkerz.crawler.core.controller.DefaultController;
import org.linkerz.crawler.core.model.WebLink;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:54 AM
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("crawlerContext.xml");
        Controller controller = context.getBean("defaultController", DefaultController.class);
        controller.start(new WebLink("http://java.dzone.com"));
    }

}
