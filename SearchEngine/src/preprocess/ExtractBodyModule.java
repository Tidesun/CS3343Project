package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.*;

public class ExtractBodyModule extends ExtractModuleAbstract implements ExtractModuleInterface {
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
		String[] rexs= {"<script[^>]*?>[\\s\\S]*?<\\/script>","<style[^>]*?>[\\s\\\\S]*?<\\/style>","</?[^>]+>","\\p{Punct}"};
		for (int i=0;i<rexs.length;i++) {
			Pattern p=Pattern.compile(rexs[i],Pattern.CASE_INSENSITIVE); 
			Matcher m=p.matcher(content); 
			content=m.replaceAll("");
		}
		return content;
	}
}
