package preprocess;

import java.io.File;
import java.io.FileNotFoundException;

/** 
* @ClassName: ExtractModuleInterface 
* @Description: TODO 
*/
public interface ExtractModuleInterface {
	
	/** 
	* @Title: WebPageExtraction 
	* @Description: TODO
	* @param @param filename
	* @param @return
	* @param @throws FileNotFoundException 
	* @return String
	* @throws 
	*/
	public abstract String WebPageExtraction(File filename) throws FileNotFoundException;

}

