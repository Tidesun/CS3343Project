package preprocess;

import java.io.File;
import java.io.FileNotFoundException;

// TODO: Auto-generated Javadoc
/** 
* Interface for web page extractor
* 
*/
public interface ExtractModuleInterface {
	
	/**
	 *  
	 * abstract method for extracting content from web page.
	 * @param filename the filename
	 * @return String
	 * @throws FileNotFoundException the file not found exception 
	 */
	public abstract String WebPageExtraction(File filename) throws FileNotFoundException;

}

