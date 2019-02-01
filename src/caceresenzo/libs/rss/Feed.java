package caceresenzo.libs.rss;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class Feed {
	
	/* Variables */
	private final String title, link, description, language, copyright, publicationDate;
	private final List<FeedMessage> entries;
	
	/* Constructor */
	public Feed(String title, String link, String description, String language, String copyright, String publicationDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.publicationDate = publicationDate;
		
		this.entries = new ArrayList<>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public String getPublicationDate() {
		return publicationDate;
	}
	
	public List<FeedMessage> getMessages() {
		return entries;
	}
	
	@Override
	public String toString() {
		return "Feed[copyright=" + copyright + ", description=" + description + ", language=" + language + ", link=" + link + ", publicationDate=" + publicationDate + ", title=" + title + "]";
	}
	
}