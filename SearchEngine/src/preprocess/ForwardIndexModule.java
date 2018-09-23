package preprocess;

import java.util.ArrayList;
import java.util.HashMap;

public class ForwardIndexModule implements ForwardIndexModuleInterface{
	private InvertedIndexModuleInterface observer;
	private HashMap<String,ArrayList<String>> ForwardIndexMap;
	
	ForwardIndexModule() {
		observer=null;
		ForwardIndexMap=new HashMap<String,ArrayList<String>>();
	}
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
		this.generateForwardIndexMap();
	}
	public void generateForwardIndexMap() {
		ArrayList<String> lst=new ArrayList<String>();
		lst.add("a");
		lst.add("b");
		this.ForwardIndexMap.put("abc",lst);
		observer.generateInvertedIndexMap(this.ForwardIndexMap);
	}
}
