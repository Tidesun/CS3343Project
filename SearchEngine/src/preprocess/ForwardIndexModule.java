package preprocess;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ForwardIndexModule implements ForwardIndexModuleInterface{
	private KeywordMapModuleInterface keywordMapModule;
	private HashMap<String, ArrayList<String>> ForwardIndexMap;
	private InvertedIndexModuleInterface observer;
	
	
	ForwardIndexModule(KeywordMapModuleInterface keywordMapModule){
		this.ForwardIndexMap= new HashMap<String, ArrayList<String>>();
		this.keywordMapModule=keywordMapModule;
		this.keywordMapModule.subscribe(this);
		this.observer=null;
	}
	
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
	}
	
	//==================== Change the origin HashMap to new HashMap ====================
	public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException{
		for (String key : origin_map.keySet()) {
			String get_url = key;
			ArrayList<String> keywordList = getKeywordFromOriginHashMap(origin_map.get(key));
			
			for (String temp_str: keywordList) {
				addKeywordsToTheHashMap(get_url, temp_str);
			}
		}
		this.writeToFile();
		this.observer.generateInvertedIndexMap(ForwardIndexMap);
	}
	
	public ArrayList<String> getKeywordFromOriginHashMap(String kwstr){ 
		ArrayList<String> kwarr = new ArrayList<String>(Arrays.asList(kwstr.split(" ")));
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
	private void writeToFile() throws IOException {		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/res/ForwardIndexDataset"));					
		oos.writeObject(this.getForwardIndexMap());					
		oos.close();
	}

}
