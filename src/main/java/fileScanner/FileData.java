package fileScanner;

/**
 * Bean class which contains the date of the file (checksum, name, size).
 */
public class FileData implements Comparable<FileData> {
	// The checksum of the file.
	private String checksum;
	
	// The full name of the file.
	private String fullName;
	
	// The size of the file.
	private long size;
	
	public FileData() {
	}
	
	public FileData(String checksum, String fullName, long size) {
		this.checksum = checksum;
		this.fullName = fullName;
		this.size = size;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Creates the sorting order in the ascending order 
	 * by file size, content and name. 
	 */
	public int compareTo(FileData fileData2) {
		if (size > fileData2.size) {
			return 1;
		} else if (size < fileData2.size) {
			return -1;
		} else if (checksum.compareTo(fileData2.checksum) > 0) {
			return 1;
		} else if (checksum.compareTo(fileData2.checksum) < 0) {
			return -1;
		} else if (fullName.compareTo(fileData2.fullName) > 0) {
			return 1;
		} else if (fullName.compareTo(fileData2.fullName) < 0) {
			return -1;
		}else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileData other = (FileData) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileData [checksum=" + checksum + ", fullName=" + fullName
				+ ", size=" + size + "]";
	}

}