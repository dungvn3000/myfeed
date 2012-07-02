package org.linkerz.crawler.core.job;

import org.linkerz.crawler.core.callback.JobCallBack;
import org.linkerz.crawler.core.downloader.DownloadResult;
import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.Parser;
import org.linkerz.crawler.core.parser.ParserResult;

import java.util.Map;

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:12 AM
 */
public class CrawlJob implements Job {

    private Map<String, Downloader> downloaders;
    private Map<String, Parser> parsers;
    private WebLink webLink;
    private JobCallBack<ParserResult> jobCallBack;

    private DownloadResult downloadResult;
    private ParserResult parserResult;

    public CrawlJob(WebLink webLink, JobCallBack<ParserResult> jobCallBack) {
        this.webLink = webLink;
        this.jobCallBack = jobCallBack;
    }

    @Override
    public void execute() {
        try {
            downloadResult = downloaders.get("*").download(webLink);
            parserResult = parsers.get("*").parse(downloadResult);
            jobCallBack.onSuccess(parserResult);
        } catch (Exception e) {
            jobCallBack.onFailed(e);
        }
    }

    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }
}
