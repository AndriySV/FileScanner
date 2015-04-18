package fileScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Sets the root directory.
 */
public class DirectoryValidator {

	private static final String EXIT_MESSAGE = "\nEnter 'quit' to exit.";

	private static final String INITIAL_MESSAGE = "Enter the path to the directory and then press the button <enter>."
			+ EXIT_MESSAGE;

	private static final String INVALID_PATH = "The directory with the current path doesn't exist. \n"
			+ "Please enter new path." + EXIT_MESSAGE;

	private static final String ENDING = File.separator;
	private static final String QUIT = "quit" + ENDING;

	private InputStreamReader inputStreamReader;
	private BufferedReader bufferRead;

	// The root directory which will be scanned.
	private static File rootDir;

	/**
	 * Sets the root directory whose content will be scanned.
	 */
	public void setRootDirectory() {
		boolean isInvalidDir = true;
		System.out.println(INITIAL_MESSAGE);

		do {
			// The path to the root directory.
			String rootPath = getPath();

			if (rootPath != null && !rootPath.equals(ENDING)) {
				// Exit from the application
				if (rootPath.equals(QUIT)) {
					rootDir = null;
					return;
				}

				rootDir = new File(rootPath);

				// Invalid path. Enter new path.
				if (!rootDir.isDirectory()) {
					System.out.println(INVALID_PATH);
				} else {
					isInvalidDir = false;
				}
			}

		} while (isInvalidDir);

		closeStreams();
	}

	/**
	 * @return the root directory whose content will be scanned.
	 */
	public File getRootDirectory() {
		return rootDir;
	}

	/**
	 * Reads the path to the root directory from the console.
	 * 
	 * @return the path to the root directory.
	 */
	private String getPath() {
		// The path to the root directory.
		String rootPath = null;

		try {
			inputStreamReader = new InputStreamReader(System.in);
			bufferRead = new BufferedReader(inputStreamReader);

			rootPath = bufferRead.readLine();
		} catch (IOException e) {
			closeStreams();
			System.err
					.println("Exception during the reading from the console.");
			e.printStackTrace();
		}
		return rootPath + ENDING;
	}

	/**
	 * Closes the streams InputStreamReader and BufferRead.
	 */
	private void closeStreams() {
		try {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (bufferRead != null) {
				bufferRead.close();
			}
		} catch (IOException e) {
			System.err.println("Exception during the closing from the stream.");
			e.printStackTrace();
		}
	}

}