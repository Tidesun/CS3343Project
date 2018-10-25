package preprocess;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


/** 
* @ClassName: ForwardIndexModule 
* @Description: TODO 
*/
public class ForwardIndexModule implements ForwardIndexModuleInterface{
	private StringMapModuleInterface keywordMapModule;
	private HashMap<String, ArrayList<String>> ForwardIndexMap;
	private InvertedIndexModuleInterface observer;
	
	private static File swlFile = new File("src/res/stopwords.txt");
	private ArrayList<String> stopwords_list = new ArrayList<String>();
	private Scanner sc;
	private String savePath;
	public ForwardIndexModule(StringMapModuleInterface keywordMapModule,String savePath){
		this.ForwardIndexMap= new HashMap<String, ArrayList<String>>();
		this.keywordMapModule=keywordMapModule;
		this.keywordMapModule.subscribe(this);
		this.savePath=savePath;
		this.observer=null;
	}
	
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
	}
	
	//==================== Remove The Stopwords Function ====================
	
	public void createTheStopwordsList() {
		try {
			sc = new Scanner(swlFile);
			while(sc.hasNextLine()) {
				String theword = sc.nextLine();
				stopwords_list.add(theword);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//==================== Change the origin HashMap to new HashMap ====================
	public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException,NullPointerException{
		if (this.observer==null)
			throw new NullPointerException ("ERROR: no observer subscribe to this.");
		createTheStopwordsList();
		
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
		String[] kwlist = kwstr.toLowerCase().split("\\s");
		ArrayList<String> kwarr=new ArrayList<String>();
		for (int i=0;i<kwlist.length;i++) {
			if (kwlist[i].length()!=0) {
				if(!stopwords_list.contains(kwlist[i])){
					kwarr.add(kwlist[i]);
				}
			}
		}
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
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.savePath));					
		oos.writeObject(this.getForwardIndexMap());					
		oos.close();
	}

}
