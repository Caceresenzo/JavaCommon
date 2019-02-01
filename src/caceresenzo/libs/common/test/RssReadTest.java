package caceresenzo.libs.common.test;

import java.io.IOException;
import java.text.ParseException;

import caceresenzo.libs.network.Downloader;
import caceresenzo.libs.rss.Feed;
import caceresenzo.libs.rss.FeedMessage;
import caceresenzo.libs.rss.RssFeedParser;

public class RssReadTest {
	
	public static void main(String[] args) throws IOException, ParseException {
		RssFeedParser parser = new RssFeedParser(Downloader.webget("https://www.jetanime.co/rss/"));
		
		Feed feed = parser.readFeed();
		System.out.println(feed);
		
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);
			// System.out.println(new Date(message.getPublicationDate()).getTime());
		}
	}
	
}