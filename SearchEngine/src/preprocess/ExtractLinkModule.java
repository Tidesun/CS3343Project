package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExtractLinkModule extends ExtractModuleAbstract implements ExtractModuleInterface{
	public String WebPageExtraction(File filename) throws FileNotFoundException {
		String content=this.getContent(filename);
		Pattern pattern = Pattern.compile("<a.*href=[\"']?([\\w\\W]*?)[\"']", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		String link_str = "";
		
		while (matcher.find()) {
			String the_str = matcher.group(1);			
			String last_part = the_str.substring(the_str.lastIndexOf("/")+1);		
			if(the_str.contains("cs.cityu.edu.hk") && the_str.contains("http") && last_part.matches("[^@<>]*$") && (last_part.contains(".htm") || !last_part.contains("."))) {
				if(last_part.contains("#")) {
					link_str += the_str.substring(the_str.indexOf("://")+3, the_str.lastIndexOf("#"))+ " ";
				}else {
					link_str += the_str.substring(the_str.indexOf("://")+3) + " ";
				}
			}
		}
		return link_str;
	}
}
