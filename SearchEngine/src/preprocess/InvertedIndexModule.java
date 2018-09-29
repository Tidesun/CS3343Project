package preprocess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
public class InvertedIndexModule implements InvertedIndexModuleInterface{
	
	//Reference the ForwardIndexModule via interface
	private ForwardIndexModuleInterface forwardIndexModule;
	
	//Store the HashMap mapping from keyword to URL lists
	private HashMap<String,ArrayList<String>> InvertedIndexMap;
	
	//Constructor
	InvertedIndexModule(ForwardIndexModuleInterface forwardIndexModule) {
		this.forwardIndexModule=forwardIndexModule;
		this.forwardIndexModule.subscribe(this);	
	}
	//Generate the InvertedIndexMap
	public void generateInvertedIndexMap(HashMap<String,ArrayList<String>> forwardIndexMap) {
		InvertedIndexMap=new HashMap<String,ArrayList<String>>();
		for (Iterator iterator = forwardIndexMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry forwardIndex = (Map.Entry) iterator.next();
			String url = (String) forwardIndex.getKey();
			ArrayList<String> keywordsList = (ArrayList<String>) forwardIndex.getValue();
			for(int i = 0; i < keywordsList.size(); i++){
				String keyword = keywordsList.get(i);
				if(!InvertedIndexMap.containsKey(keyword)){
					ArrayList<String> urlsList = new ArrayList<String>();
					urlsList.add(url);
					InvertedIndexMap.put(keyword, urlsList);
				}
				else{
					ArrayList<String> urls = InvertedIndexMap.get(keyword);
					if(!urls.contains(url))
						urls.add(url);
				}
			}
		}
	}
	
	//Get the InvertedIndexMap
	public HashMap<String,ArrayList<String>> getInvertedIndexMap(){
		return InvertedIndexMap;
	}
}
