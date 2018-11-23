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

// TODO: Auto-generated Javadoc
/**
 * The Class ForwardIndexModule.
 */
public class ForwardIndexModule implements ForwardIndexModuleInterface{
	
	/** The keyword map module. */
	private StringMapModuleInterface keywordMapModule;
	
	/** The Forward index map. */
	private HashMap<String, ArrayList<String>> ForwardIndexMap;
	
	/** The observer. */
	private InvertedIndexModuleInterface observer;
	
	/** The swl file. */
	private static File swlFile = new File("src/res/stopwords.txt");
	
	/** The stopwords list. */
	private ArrayList<String> stopwords_list = new ArrayList<String>();
	
	/** The sc. */
	private Scanner sc;
	
	/** The save path. */
	private String savePath;
	
	/**
	 * Instantiates a new forward index module.
	 *
	 * @param keywordMapModule the keyword map module
	 * @param savePath the save path
	 */
	public ForwardIndexModule(StringMapModuleInterface keywordMapModule,String savePath){
		this.ForwardIndexMap= new HashMap<String, ArrayList<String>>();
		this.keywordMapModule=keywordMapModule;
		this.keywordMapModule.subscribe(this);
		this.savePath=savePath;
		this.observer=null;
	}
	
	/**
	 * Title: subscribe
	 * Description: let InvertedIndexModule subscribe to this module.
	 *
	 * @param observer the observer
	 * @see preprocess.ForwardIndexModuleInterface#subscribe(preprocess.InvertedIndexModuleInterface)
	 */
	public void subscribe(InvertedIndexModuleInterface observer) {
		this.observer=observer;
	}
	
	/**
	 * Creates the the stopwords list.
	 */
	/*
	* @Title: createTheStopwordsList 
	* @Description: create stop words list from file
	* @param  
	* @return void
	* @throws 
	*/
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
	
	/*
	* Title: generateForwardIndexMap
	* Description: generate a fowardIndexMap
	* @param origin_map
	* @throws IOException
	* @throws NullPointerException 
	* @see preprocess.ForwardIndexModuleInterface#generateForwardIndexMap(java.util.HashMap) 
	*/
	public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException,NullPointerException{
		
		
		for (String key : origin_map.keySet()) {
			String get_url = key;
			ArrayList<String> keywordList = getKeywordFromOriginHashMap(origin_map.get(key));
			
			for (String temp_str: keywordList) {
				addKeywordsToTheHashMap(get_url, temp_str);
			}
		}
		this.writeToFile();
		if (this.observer==null)
			throw new NullPointerException ("ERROR: no observer subscribe to this.");
		else
			this.observer.generateInvertedIndexMap(ForwardIndexMap);
	}
	
	/**
	 * Gets the keyword from origin hash map.
	 *
	 * @param kwstr the kwstr
	 * @return the keyword from origin hash map
	 */
	/*
	* @Title: getKeywordFromOriginHashMap 
	* @Description: Change the origin HashMap to new HashMap
	* @param kwstr
	* @return ArrayList<String>
	* @throws 
	*/
	public ArrayList<String> getKeywordFromOriginHashMap(String kwstr){ 
		String[] kwlist = kwstr.toLowerCase().split(" ");
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
	
	/**
	 * Adds the keywords to the hash map.
	 *
	 * @param the_url the the url
	 * @param keyword the keyword
	 */
	/*
	* @Title: addKeywordsToTheHashMap 
	* @Description: add keywords to the hashmap
	* @param the_url
	* @param keyword 
	* @return void
	* @throws 
	*/
	public void addKeywordsToTheHashMap(String the_url, String keyword) {
		if(!ForwardIndexMap.containsKey(the_url)){
			createArrayListOfURL(the_url);
		}
		
		ArrayList<String> arrayList = ForwardIndexMap.get(the_url);
		ArrayList<String> get_arr = arrayList;
		get_arr.add(keyword);
		ForwardIndexMap.put(the_url, get_arr);
	}
	
	/**
	 * Creates the array list of URL.
	 *
	 * @param the_url the the url
	 */
	public void createArrayListOfURL(String the_url){
		ArrayList<String> arrlist = new ArrayList<String>();
		ForwardIndexMap.put(the_url, arrlist);
	}
	
	/*
	* Title: getForwardIndexMap
	* Description: Get ForwardIndexMap
	* @return ForwardIndexMap
	* @see preprocess.ForwardIndexModuleInterface#getForwardIndexMap() 
	*/
	public HashMap<String,ArrayList<String>> getForwardIndexMap(){
		return ForwardIndexMap;
	}
	
	/**
	 * Write to file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*
	* @Title: writeToFile 
	* @Description: Write the forwardIndex to file
	* @param @throws IOException 
	* @return void
	* @throws 
	*/
	private void writeToFile() throws IOException {		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.savePath));					
		oos.writeObject(this.getForwardIndexMap());					
		oos.close();
	}

}
