package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;
public class HeaderModule implements KeywordExtractModuleInterface {
	
	private String getContent(File filename) throws FileNotFoundException {
		Scanner sc =new Scanner(filename);
		String content="";
		while(sc.hasNext()){
			content +=sc.nextLine()+"\n";
		}
		sc.close();
		return content;
	}
	public String WebPageExtraction(File filename) throws FileNotFoundException {
		String content=this.getContent(filename);
		String regex="(?<=<title>).*?(?=</title>)";
		Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(0).toLowerCase();
		}
		else 
			return null;
	}

}
