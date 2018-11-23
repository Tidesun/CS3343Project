package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * Abstract class for extractor.
 */
public class ExtractModuleAbstract {
	
	/**
	 *  
	 *
	 * @param filename the filename
	 * @return String
	 * @throws FileNotFoundException the file not found exception
	 */
	protected String getContent(File filename) throws FileNotFoundException {
		Scanner sc =new Scanner(filename);
		String content="";
		while(sc.hasNext()){
			content +=sc.nextLine()+"\n";
		}
		sc.close();
		return content;
	}
}
