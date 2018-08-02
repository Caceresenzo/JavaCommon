package caceresenzo.libs.comparator;

/*
Version a = new Version("1.1");
Version b = new Version("1.1.1");
a.compareTo(b) // return -1 (a<b)
a.equals(b)    // return false

Version a = new Version("2.0");
Version b = new Version("1.9.9");
a.compareTo(b) // return 1 (a>b)
a.equals(b)    // return false

Version a = new Version("1.0");
Version b = new Version("1");
a.compareTo(b) // return 0 (a=b)
a.equals(b)    // return true

Version a = new Version("1");
Version b = null;
a.compareTo(b) // return 1 (a>b)
a.equals(b)    // return false

List<Version> versions = new ArrayList<Version>();
versions.add(new Version("2"));
versions.add(new Version("1.0.5"));
versions.add(new Version("1.01.0"));
versions.add(new Version("1.00.1"));
Collections.min(versions).get() // return min version
Collections.max(versions).get() // return max version

// WARNING
Version a = new Version("2.06");
Version b = new Version("2.060");
a.equals(b)    // return false
 */
public class Version implements Comparable<Version> {
	
	private String version;
	private VersionType versionType;
	
	public Version(String version) {
		this(version, VersionType.RELEASE);
	}
	
	public Version(String version, VersionType versionType) {
		if (version == null) {
			throw new IllegalArgumentException("Version can not be null");
		}
		if (!version.matches("[0-9]+(\\.[0-9]+)*")) {
			throw new IllegalArgumentException("Invalid version format");
		}
		this.version = version;
		this.versionType = versionType;
	}
	
	public final String get(boolean withType) {
		return this.version + (withType ? (versionType.isNeedToShow() ? " " + versionType.toString() : "") : "");
	}
	
	public final String get() {
		return get(true);
	}
	
	public final VersionType getType() {
		return this.versionType;
	}
	
	@Override
	public int compareTo(Version that) {
		if (that == null) {
			return 1;
		}
		
		if (versionType.ordinal() < that.getType().ordinal()) {
			return -1;
		}
		if (versionType.ordinal() > that.getType().ordinal()) {
			return 1;
		}
		
		String[] thisParts = this.get(false).split("\\.");
		String[] thatParts = that.get(false).split("\\.");
		int length = Math.max(thisParts.length, thatParts.length);
		for (int i = 0; i < length; i++) {
			int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
			int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
			if (thisPart < thatPart) {
				return -1;
			}
			if (thisPart > thatPart) {
				return 1;
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (this.getClass() != that.getClass()) {
			return false;
		}
		return this.compareTo((Version) that) == 0;
	}
	
}