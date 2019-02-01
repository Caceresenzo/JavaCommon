package caceresenzo.libs.rss;

import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RssFeedParser {
	
	/* Constants */
	public static final String PART_TITLE = "title";
	public static final String PART_DESCRIPTION = "description";
	public static final String PART_CHANNEL = "channel";
	public static final String PART_LANGUAGE = "language";
	public static final String PART_COPYRIGHT = "copyright";
	public static final String PART_LINK = "link";
	public static final String PART_AUTHOR = "author";
	public static final String PART_ITEM = "item";
	public static final String PART_PUBLICATION_DATE = "pubDate";
	public static final String PART_GUID = "guid";
	
	/* Variables */
	private final String rss;
	
	/* Constructor */
	public RssFeedParser(String rss) {
		this.rss = rss;
	}
	
	public Feed readFeed() {
		Feed feed = null;
		try {
			boolean isFeedHeader = true;
			String description = "", title = "", link = "", language = "", copyright = "", author = "", publicationDate = "", guid = "";
			
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(new StringReader(rss));
			
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					
					switch (localPart) {
						case PART_ITEM: {
							if (isFeedHeader) {
								isFeedHeader = false;
								feed = new Feed(title, link, description, language, copyright, publicationDate);
							}
							event = eventReader.nextEvent();
							break;
						}
							
						case PART_TITLE: {
							title = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_DESCRIPTION: {
							description = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_LINK: {
							link = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_GUID: {
							guid = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_LANGUAGE: {
							language = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_AUTHOR: {
							author = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_PUBLICATION_DATE: {
							publicationDate = getCharacterData(event, eventReader);
							break;
						}
							
						case PART_COPYRIGHT: {
							copyright = getCharacterData(event, eventReader);
							break;
						}
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == PART_ITEM) {
						FeedMessage message = new FeedMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						message.setPublicationDate(publicationDate);
						
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						
						continue;
					}
				}
			}
		} catch (XMLStreamException exception) {
			throw new RuntimeException(exception);
		}
		
		return feed;
	}
	
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String result = "";
		
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		
		return result;
	}
	
}