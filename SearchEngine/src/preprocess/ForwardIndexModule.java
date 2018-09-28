package SearchTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ForwardIndexModule implements ForwardIndexModuleInterface{
	//private InvertedIndexModuleInterface observer;
	private static HashMap<String, ArrayList<String>> forwardMap  = new HashMap<String, ArrayList<String>>();
		
	/*
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
		this.generateForwardIndexMap();
	}
	*/
	
	//==================== Change the origin HashMap to new HashMap ====================
	public void generateForwardIndexMap(HashMap<String, String> origin_map){
		for (String key : origin_map.keySet()) {
			String get_url = key;
			ArrayList<String> keywordList = getKeywordFromOriginHashMap(origin_map.get(key));
			
			for (String temp_str: keywordList) {
				addKeywordsToTheHashMap(get_url, temp_str);
			}
		}
	}
	
	public static ArrayList<String> getKeywordFromOriginHashMap(String kwstr){
		System.out.println("The Str: "+kwstr);  
		ArrayList<String> kwarr = new ArrayList<String>(Arrays.asList(kwstr.split("\\s*,\\s*")));
		
		return kwarr;
	}
	
	public static void addKeywordsToTheHashMap(String the_url, String keyword) {
		if(!forwardMap.containsKey(the_url)){
			createArrayListOfURL(the_url);
		}
		
		ArrayList<String> arrayList = forwardMap.get(the_url);
		ArrayList<String> get_arr = arrayList;
		get_arr.add(keyword);
		forwardMap.put(the_url, get_arr);
	}
	
	public static void createArrayListOfURL(String the_url){
		ArrayList<String> arrlist = new ArrayList<String>();
		forwardMap.put(the_url, arrlist);
	}
	
	//==================== Get ForwardIndexMap ====================
	public HashMap<String, ArrayList<String>> getForwardIndexMap(){
		return forwardMap;
	}

}
