package fileScanner;

import java.util.List;

/**
 * Displays all files.
 */
public class ShowResult {
	
	// Delimits the files groups with the different content.
	private static final String GROUP_DELIMITER = 
		"----------------------------------------------------------------------";
	
	// The title of the table with the grouped data of files.
	private static final String TITLE = "Size  -  Name";
	
	/**
	 * Displays all files.
	 */
	public static void displayGroupedFiles(List<FileData> fileList){
		// Checksum from the previous file.
		String previousChecksum = "";
		
		// Checksum from the current file.
		String currentChecksum  = "";
		
		System.out.println(GROUP_DELIMITER);
		System.out.println(TITLE);
		
		for (int i = 0; i < fileList.size(); i++) {
			currentChecksum = fileList.get(i).getChecksum();

			if (!previousChecksum.equals(currentChecksum)) {
				System.out.println(GROUP_DELIMITER);
			}
			
			System.out.println(fileList.get(i).getSize() + "  -  " 
					+ fileList.get(i).getFullName());
			
			previousChecksum = currentChecksum;
		}
	}
	
}