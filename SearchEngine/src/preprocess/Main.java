package preprocess;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) {
		ForwardIndexModuleInterface mockForward=new ForwardIndexModule();
		InvertedIndexModuleInterface mockInvert=new InvertedIndexModule(mockForward);
		String headstr="HTML,CSS,JavaScript,DOM,jQuery,PHP,SQL,XML,Python,Bootstrap,Web,W3CSS,W3C,tutorials,programming,development,training,learning,quiz,primer,lessons,reference,examples,source code,colors,demos,tips,w3c";
		HashMap<String,String> testmap=new HashMap<String, String>();
		testmap.put("www.w3c.com",headstr);
		mockForward.generateForwardIndexMap(testmap);
		System.out.println(mockInvert.getInvertedIndexMap());
		
	}
}
