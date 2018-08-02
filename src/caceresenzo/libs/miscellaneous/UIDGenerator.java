package caceresenzo.libs.miscellaneous;

import java.util.ArrayList;

import caceresenzo.libs.parse.ParseUtils;
import caceresenzo.libs.random.Randomizer;

public class UIDGenerator {
	
	private final int length;
	
	private final String string; // Represents how long the UID is "9999" = 4
	
	private final ArrayList<Integer> previousUID;
	
	public UIDGenerator(final String string) {
		length = string.length();
		this.string = string;
		previousUID = new ArrayList<Integer>();
	}
	
	public int generateUID() {
		final String id = Randomizer.advancedRandomRangeInteger(0, ParseUtils.parseInt(string, 9999)) + "";
		if (!(id.length() == length)) {
			return generateUID();
		} else {
			final int idNumber = Integer.parseInt(id);
			if (!previousUID.contains(idNumber)) {
				previousUID.add(idNumber);
				return idNumber;
			} else if (previousUID.contains(idNumber)) {
				return generateUID();
			} else {
				return -1;
			}
		}
	}
	
}