package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// TODO: Auto-generated Javadoc
/**
 * Extract link from web page.
 */
public class ExtractLinkModule extends ExtractModuleAbstract implements ExtractModuleInterface{
	
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
		Pattern pattern = Pattern.compile("<a.*href=[\"']?([\\w\\W]*?)[\"']", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		String link_str = "";
		
		while (matcher.find()) {
			String the_str = matcher.group(1);			
			String last_part = the_str.substring(the_str.lastIndexOf("/")+1);		
			if(the_str.contains(Preprocess.domain) && the_str.contains("http") && last_part.matches("[^@<>]*$") && (last_part.contains(".htm") || !last_part.contains("."))) {
				if(last_part.contains("#")) {
					link_str += the_str.substring(the_str.indexOf("://")+3, the_str.lastIndexOf("#"))+ " ";
				}else {
					link_str += the_str.substring(the_str.indexOf("://")+3) + " ";
				}
			}
		}
		return link_str.trim();
	}
}
