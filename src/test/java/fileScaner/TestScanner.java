package fileScaner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import fileScanner.FileData;
import fileScanner.Scanner;
import static org.testng.Assert.*;

/**
 * Tests the Scanner class.
 */
@Test
public class TestScanner {

	private static final String PROPERTY_PATH = "/file_content.properties"; 
	private static final String CONTENT_1 ="content1";
	private static final String CONTENT_2 ="content2";
	
	private static final String ROOT_PATH = "FileScannerTest" + File.separator;
	
	private static final String DIR_1 = "test1"  + File.separator;
	private static final String DIR_2 = "test2" + File.separator;
	
	private static final String FILE_1 = ROOT_PATH + DIR_1 + "file1.txt";
	private static final String FILE_2 = ROOT_PATH + DIR_1 + DIR_2 + "file2.txt";
	private static final String FILE_3 = ROOT_PATH + "file3.txt";
	
	private static final String SKIP_MESSAGE = 
			"Can't create testing directory. Maybe you doen't have permissionss";
	
	private InputStream inputStream; 
	private BufferedInputStream bufferedInputStream;
	private PrintWriter writer;
	
	private static List<FileData> fileList;
	private static List<FileData> expectedFileList;
	
	private FileData fileData;
	
	private Properties properties;
	private Scanner scanner;
	private File rootDir;
	
	private File[] files;
	
	/**
	 * Creates the test root directory.
	 */
	@BeforeClass
	private void createTestDirectory() {
		scanner = new Scanner();
		rootDir = new File(ROOT_PATH);
		expectedFileList = new ArrayList<FileData>();
		properties = new Properties();
		
		boolean isSuccess = rootDir.mkdir();
		// Skip test, if directory wasn't created.
		if(!isSuccess) {
			System.out.println(SKIP_MESSAGE);
			throw new SkipException(SKIP_MESSAGE);
		}
		
		files = new File[] {
				new File(FILE_1), 
				new File(FILE_2),
				new File(FILE_3)
			};
		
		for (int i = 0; i < files.length; i++) {
			createSubDirectory(files[i]);
			// Fill all files.
			switch (i) {
			case 0:
				fillFile(files[i], getContent(CONTENT_1));
				fillExpectedFileList(files[i], FILE_1);
				break;
			case 1:
				fillFile(files[i], getContent(CONTENT_2));
				fillExpectedFileList(files[i], FILE_2);
				break;
			case 2:
				fillFile(files[i], getContent(CONTENT_1));
				fillExpectedFileList(files[i], FILE_3);
				break;
			}
			
		}
		
		Collections.sort(expectedFileList);
	}
	
	/**
	 * @param content - it's a key, which is used in the property file.
	 * @return - content which is associated with current key. 
	 */
	private String getContent(String content) {
		try {
			inputStream = this.getClass().getResourceAsStream(PROPERTY_PATH);
			bufferedInputStream = new BufferedInputStream(inputStream);
			properties.load(bufferedInputStream);
		} catch (IOException e) {
			System.err.println("Exception during the reading from the file_content.properties.");
			e.printStackTrace();
		} finally{
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				System.err.println("Exception during the closing stream.");
				e.printStackTrace();
			}
		}
		
		return properties.getProperty(content);
	}
	
	/**
	 * Fills expected file list.
	 */
	private void fillExpectedFileList(File file, String fileName) {
		fileData = new FileData(scanner.getChecksum(file), fileName , file.length());
		expectedFileList.add(fileData);
	}

	/**
	 * @param file - it's a file which will be filled. 
	 * @param text - it's a text which will be written in the file.
	 */
	private void fillFile(File file, String text) {
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.print(text);
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Exception during closing the stream.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.err.println("Exception during choosing unsupported encoding.");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Creates all subdirectories.
	 */
	private void createSubDirectory(File file) {
		try {
			file.getParentFile().mkdirs();	
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("Exception during the creating subdirectories.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method findAllFiles() from the class Scanner.
	 */
	@Test
	public void testFindAllFiles(){
		fileList = scanner.findAllFiles(rootDir);
		
		assertNotNull(fileList);
		assertEquals(fileList, expectedFileList);
	}
	
	/**
	 * Deletes the root directory and all its content after testing.
	 */
	@AfterClass
	private void deleteTestDirectory() {
		try {
			FileUtils.deleteDirectory(rootDir);
		} catch (IOException e) {
			System.err.println("Exception during the deleting root directory.");
			e.printStackTrace();
		}
	}
	
}