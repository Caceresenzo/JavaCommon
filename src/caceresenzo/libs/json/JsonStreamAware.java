package caceresenzo.libs.json;

import java.io.IOException;
import java.io.Writer;

public interface JsonStreamAware {

	void writeJsonString(Writer out) throws IOException;

}
