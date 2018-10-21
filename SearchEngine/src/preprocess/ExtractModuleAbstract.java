package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExtractModuleAbstract {
	/** 
	* @Title: getContent 
	* @Description: TODO
	* @param @param filename
	* @param @return
	* @param @throws FileNotFoundException 
	* @return String
	* @throws 
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
