package preprocess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		ForwardIndexModuleInterface mockForward=new ForwardIndexModule();
		InvertedIndexModuleInterface mockInvert=new InvertedIndexModule(mockForward);
		String headstr="HTML,CSS,JavaScript,DOM,jQuery,PHP,SQL,XML,Python,Bootstrap,Web,W3CSS,W3C,tutorials,programming,development,training,learning,quiz,primer,lessons,reference,examples,source code,colors,demos,tips,w3c";
		HashMap<String,String> testmap=new HashMap<String, String>();
		testmap.put("www.w3c.com",headstr);
		mockForward.generateForwardIndexMap(testmap);
	}
}
