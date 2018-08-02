package caceresenzo.libs.json.parser;

import java.io.IOException;

public interface ContentHandler {

	void startJSON() throws JsonException, IOException;

	void endJSON() throws JsonException, IOException;

	boolean startObject() throws JsonException, IOException;

	boolean endObject() throws JsonException, IOException;

	boolean startObjectEntry(String key) throws JsonException, IOException;

	boolean endObjectEntry() throws JsonException, IOException;

	boolean startArray() throws JsonException, IOException;

	boolean endArray() throws JsonException, IOException;

	boolean primitive(Object value) throws JsonException, IOException;

}