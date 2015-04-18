package fileScanner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Scans the root directory finds all files 
 * and grouped their.
 */
public class Scanner {
	
	// Contains the list of all files and their data.
	private ArrayList<FileData> fileList;
	
	// The name of the file or directory inside the rootDir.
	private String elementName;
	
	// The file or directory inside the rootDir.
	private File element;
	
	// The size of the file.
	private long fileSize;
	
	private FileInputStream fileInputStream;
	private BufferedInputStream bufferedInputStream;

	// The checksum of the file.
	private String checksum;
	
	// Contains the data about the file.
	private FileData fileData;
	
	/**
	 * Creates the list which will be filled in the fileData objects.
	 */
	public Scanner() {
		fileList = new ArrayList<FileData>();
	}
	
	/**
	 *  Finds all files which are contained in root directory and all its directories.
	 *  Puts the files data in the fileList. 
	 *  Groups the fileList by the files contents 
	 *  and sorts it in the ascending order by files sizes.
	 *  
	 * @param rootDir - the root directory in which the files search will be exercised.
	 * @return list of all found files.
	 */
	public List<FileData> findAllFiles(File rootDir) {
		// Massive of all files or directories names which are contained in current directory.
		String[] dirElements = rootDir.list();

		for (int i = 0; i < dirElements.length; i++) {
			elementName = rootDir.getPath() + File.separator + dirElements[i];
			element = new File(elementName);

			if (element.isDirectory()) {
				findAllFiles(element);
			} else {
				fileSize = element.length();

				fileData = new FileData(getChecksum(element), elementName, fileSize);
				fileList.add(fileData);
			}
		}
		
		// Groups the fileList by the files contents 
		// and sorts it in the ascending order by files sizes and names.
		Collections.sort(fileList);
		
		return fileList;
	}

	/**
	 * @param file - file whose checksum will be returned.
	 * @return the checksum of the specified file.
	 */
	public String getChecksum(File file) {
		try {
			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			
			checksum = DigestUtils.sha256Hex(bufferedInputStream);
		
		} catch (FileNotFoundException e) {
			System.err.println("File was not find.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Exception during the reading from the stream.");
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				System.err.println("Exception during the closing from the stream.");
				e.printStackTrace();
			}
		}
		return checksum;
	}
	
}