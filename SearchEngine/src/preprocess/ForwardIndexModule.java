package preprocess;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ForwardIndexModule implements ForwardIndexModuleInterface{
	//private InvertedIndexModuleInterface observer;
	private HashMap<String, ArrayList<String>> ForwardIndexMap;
	private InvertedIndexModuleInterface observer;
	ForwardIndexModule(){
		this.ForwardIndexMap= new HashMap<String, ArrayList<String>>();
		this.observer=null;
	}
	
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
	}
	
	//==================== Change the origin HashMap to new HashMap ====================
	public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException, FileNotFoundException{
		for (String key : origin_map.keySet()) {
			String get_url = key;
			ArrayList<String> keywordList = getKeywordFromOriginHashMap(origin_map.get(key));
			
			for (String temp_str: keywordList) {
				addKeywordsToTheHashMap(get_url, temp_str);
			}
		}
		this.observer.generateInvertedIndexMap(ForwardIndexMap);
	}
	
	public ArrayList<String> getKeywordFromOriginHashMap(String kwstr){
		System.out.println("The Str: "+kwstr);  
		ArrayList<String> kwarr = new ArrayList<String>(Arrays.asList(kwstr.split("\\s*,\\s*")));
		
		return kwarr;
	}
	
	public void addKeywordsToTheHashMap(String the_url, String keyword) {
		if(!ForwardIndexMap.containsKey(the_url)){
			createArrayListOfURL(the_url);
		}
		
		ArrayList<String> arrayList = ForwardIndexMap.get(the_url);
		ArrayList<String> get_arr = arrayList;
		get_arr.add(keyword);
		ForwardIndexMap.put(the_url, get_arr);
	}
	
	public void createArrayListOfURL(String the_url){
		ArrayList<String> arrlist = new ArrayList<String>();
		ForwardIndexMap.put(the_url, arrlist);
	}
	
	//==================== Get ForwardIndexMap ====================
	public HashMap<String,ArrayList<String>> getForwardIndexMap(){
		return ForwardIndexMap;
	}

}
