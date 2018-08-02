package caceresenzo.libs.comparator;

public enum VersionType {
	
	BETA(true), //
	ALPHA(true), //
	RELEASE(false), //
	BUILD(false); //
	
	private final boolean needToShow;
	
	private VersionType(boolean needToShow) {
		this.needToShow = needToShow;
	}
	
	public boolean isNeedToShow() {
		return needToShow;
	}
	
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