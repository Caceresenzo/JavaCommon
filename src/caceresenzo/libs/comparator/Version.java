package caceresenzo.libs.comparator;

import java.util.Collections;
import java.util.Objects;

/**
 * This class help comparing version with a simple object.<br>
 * <br>
 * Usage:<br>
 * <code>
 * Version a = new Version("1.1");<br>
 * Version b = new Version("1.1.1");<br>
 * a.compareTo(b) will return -1 (because a &lt; b)<br>
 * a.equals(b) will return false
 * </code> <br>
 * <br>
 * 
 * <code>
 * Version a = new Version("2.0");<br>
 * Version b = new Version("1.9.9");<br>
 * a.compareTo(b); // will return 1 (because a &gt; b)<br>
 * a.equals(b); // will return false<br>
 * </code> <br>
 * <br>
 * 
 * <code>
 * Version a = new Version("1.0");<br>
 * Version b = new Version("1");<br>
 * a.compareTo(b) will return 0 (because a = b)<br>
 * a.equals(b); // will return true<br>
 * </code> <br>
 * <br>
 * 
 * <code>
 * Version a = new Version("1");<br>
 * Version b = null;<br>
 * a.compareTo(b); // will return 1 (because a &gt; b)<br>
 * a.equals(b); // will return false<br>
 * </code> <br>
 * <br>
 * 
 * It can also be used with the {@link Collections} sorter:<br>
 * <code>
 * List<Version> versions = new ArrayList<Version>();<br>
 * versions.add(new Version("2"));<br>
 * versions.add(new Version("1.0.5"));<br>
 * versions.add(new Version("1.01.0"));<br>
 * versions.add(new Version("1.00.1"));<br>
 * Collections.min(versions).get(); // will return min version<br>
 * Collections.max(versions).get(); // will return max version<br>
 * </code> <br>
 * <br>
 * 
 * Warning:<br>
 * <code>
 * Version a = new Version("2.06");<br>
 * Version b = new Version("2.060");<br>
 * a.equals(b); // will return false because the {@link #equals(Object)} will compare the <code>source</code> String of both object.<br>
 * </code>
 * 
 * @author Enzo CACERES
 */
public class Version implements Comparable<Version> {
	
	/* Variables */
	private String source;
	private VersionType versionType;
	
	/* Constructor */
	/** @see Version#Version(String, VersionType) Called constructor with <code>versionType</code> as {@link VersionType#RELEASE RELEASE}. */
	public Version(String version) {
		this(version, VersionType.RELEASE);
	}
	
	/* Constructor */
	/**
	 * Create a {@link Version} object.
	 * 
	 * @param source
	 *            Source string.
	 * @param versionType
	 *            Type of version, will influate the comparing method.
	 * @throws NullPointerException
	 *             If the <code>source</code> is <code>null</code>.
	 * @throws NullPointerException
	 *             If the <code>versionType</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             If the <code>source</code> don't match the regex <code>[0-9]+(\\.[0-9]+)*</code>.
	 */
	public Version(String source, VersionType versionType) {
		Objects.requireNonNull(source, "Source can't be null.");
		Objects.requireNonNull(versionType, "Version type can't be null.");
		
		if (!source.matches("[0-9]+(\\.[0-9]+)*")) {
			throw new IllegalArgumentException("Invalid version format.");
		}
		
		this.source = source;
		this.versionType = versionType;
	}
	
	/**
	 * @return The {@link #get(boolean)} function content but with the <code>withType</code> set to <code>true</code>.
	 * @see #get(boolean)
	 */
	public String get() {
		return get(true);
	}
	
	/**
	 * Get the {@link #source} string.
	 * 
	 * @param withType
	 *            Add the {@link VersionType} at the end.
	 * @return A formatted {@link String}.
	 */
	public String get(boolean withType) {
		return this.source + (withType ? (versionType.isNeedToShow() ? " " + versionType.toString() : "") : "");
	}
	
	/** @return The version's type. */
	public VersionType getType() {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((versionType == null) ? 0 : versionType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		
		if (object == null) {
			return false;
		}
		
		if (getClass() != object.getClass()) {
			return false;
		}
		
		Version other = (Version) object;
		
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		
		if (versionType != other.versionType) {
			return false;
		}
		
		return true;
	}
	
}