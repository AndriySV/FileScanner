package fileScanner;

import java.io.File;
import java.util.List;

/**
 * Starts the application which scans the specified directory 
 * finds all files, groups and displays their.
 */
public class Main {

	// The root directory which will be scanned.
	private static File rootDir;
	
	// Contains the list of all found files.
	private static List<FileData> fileList;
	
	/**
	 * Invokes the methods which set the root directory,
	 * scan its content and display the result.
	 */
	public static void main(String[] args) {
		DirectoryValidator directoryValidator = new DirectoryValidator();
		Scanner scanner = new Scanner();
		
		directoryValidator.setRootDirectory();
		rootDir = directoryValidator.getRootDirectory();
		
		if (rootDir != null) {
			fileList = scanner.findAllFiles(rootDir);
			ShowResult.displayGroupedFiles(fileList);
		}
	}
	
}