package org.linkerz.crawler.main;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;

import java.util.Queue;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:54 AM
 */
public class Main {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName("dev").setPassword("dev");
        clientConfig.addAddress("127.0.0.1");
        HazelcastClient client = HazelcastClient.newHazelcastClient(clientConfig);
        CrawlJob crawlJob = new CrawlJob(new WebLink("http://vnexpress.net"));
        Queue jobQueue =  client.getQueue("jobQueue");
        jobQueue.add(crawlJob);
    }
}
