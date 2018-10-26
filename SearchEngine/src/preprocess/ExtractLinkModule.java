package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExtractLinkModule extends ExtractModuleAbstract implements ExtractModuleInterface{
	public String WebPageExtraction(File filename) throws FileNotFoundException {
		String content = getContent(filename);
		Pattern pattern = Pattern.compile("<a\\s*href=\"?([\\w\\W]*?)\"?[\\s]*?[^>]>([\\s\\S]*?)(?=</a>)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		String link_str = "";
		
		while (matcher.find()) {
			String the_str = matcher.group(1);
			String[] split_str = the_str.split("/");
			String last_part = split_str[split_str.length-1];
			
			if(the_str.contains("cityu.edu.hk") && (last_part.contains(".htm") || !last_part.contains("."))) {
				link_str += the_str + "\n";
			}
			System.out.println(link_str);
		}
		return link_str;
	}
}
