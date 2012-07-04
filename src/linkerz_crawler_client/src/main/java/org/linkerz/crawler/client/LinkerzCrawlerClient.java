/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.client;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import org.linkerz.core.job.Job;

/**
 * The Class LinkerzCrawlerClient.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:25 AM
 */
public final class LinkerzCrawlerClient {

    private static HazelcastClient client;

    private LinkerzCrawlerClient() {
    }

    public static LinkerzCrawlerClient connect(String ipAddress, String userName, String passWord) {
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getGroupConfig().setName(userName).setPassword(passWord);
            clientConfig.addAddress(ipAddress);
            client = HazelcastClient.newHazelcastClient(clientConfig);
        }
        return new LinkerzCrawlerClient();
    }

    public void addJob(Job job) {
        client.getQueue(Job.JOB_QUEUE).add(job);
    }

    public void shutdown() {
        client.shutdown();
    }
}
