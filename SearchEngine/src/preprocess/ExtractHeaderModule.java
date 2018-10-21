package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.*;
/** 
* @ClassName: ExtractHeaderModule 
* @Description: TODO 
*/
public class ExtractHeaderModule extends ExtractModuleAbstract implements ExtractModuleInterface  {
	
	/*
	* Title: WebPageExtraction
	* Description: 
	* @param filename
	* @return
	* @throws FileNotFoundException 
	* @see preprocess.ExtractModuleInterface#WebPageExtraction(java.io.File) 
	*/
	public String WebPageExtraction(File filename) throws FileNotFoundException {
		String content=this.getContent(filename);
		//extract content from the description
		String regex="(?<=<meta name=\\\"description\\\" content=\\\").*?(?=\")";
		Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			// replace the punctuation
			return matcher.group(0).toLowerCase().replaceAll("[\\pP\\p{Punct}]","");
		}
		else 
			return null;
	}

}
