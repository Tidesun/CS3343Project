package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.*;
// TODO: Auto-generated Javadoc
/** 
* Extract web page title from HTML file
* 
*/
public class ExtractTitleModule extends ExtractModuleAbstract implements ExtractModuleInterface  {
	
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
		String regex="<title>(.*?)<\\/title>";
		Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String the_str = matcher.group(1);
			return the_str.trim();
		}
		else 
			return null;
	}

}
