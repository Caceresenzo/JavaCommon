package caceresenzo.libs.comparator;

public enum VersionType {
	
	BETA(true), //
	ALPHA(true), //
	RELEASE(false), //
	BUILD(false); //
	
	/* Variables */
	private final boolean needToShow;
	
	/* Constructor */
	private VersionType(boolean needToShow) {
		this.needToShow = needToShow;
	}
	
	/** @return Weather or not the {@link #toString()} should normally appear after the version. */
	public boolean isNeedToShow() {
		return needToShow;
	}
	
	/**
	 * Get a corresponding {@link VersionType} from a source <code>string</code>.
	 * 
	 * @param string
	 *            Source string.
	 * @return Corresponding enum type or {@link #BUILD} if not found.
	 */
	public static VersionType fromString(String string) {
		if (string == null) {
			return BUILD;
		}
		
		for (VersionType type : VersionType.values()) {
			if (string.equalsIgnoreCase(type.toString())) {
				return type;
			}
		}
		
		return BUILD;
	}
	
}