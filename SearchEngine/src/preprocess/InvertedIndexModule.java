package preprocess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class InvertedIndexModule implements InvertedIndexModuleInterface{
	
	//Reference the ForwardIndexModule via interface
	private ForwardIndexModuleInterface forwardIndexModule;
	
	//Store the HashMap mapping from keyword to URL lists
	private HashMap<String,ArrayList<String>> InvertedIndexMap;
	
	//Constructor
	public InvertedIndexModule(ForwardIndexModuleInterface forwardIndexModule) {
		this.forwardIndexModule=forwardIndexModule;
		this.forwardIndexModule.subscribe(this);	
	}
	//Generate the InvertedIndexMap
	public void generateInvertedIndexMap(HashMap<String,ArrayList<String>> forwardIndexMap) throws IOException{
		
		InvertedIndexMap=new HashMap<String,ArrayList<String>>();
		//for each url in forwardIndexMap
		for (Map.Entry<String, ArrayList<String>> entry : forwardIndexMap.entrySet()){
			String url=entry.getKey();
			ArrayList<String> keywordsList=entry.getValue();
			//for each keyword in the keywordslist of that url
			for (int i=0;i<keywordsList.size();i++) {
				String keyword=keywordsList.get(i);
				//if the invertedIndexMap not contains this keyword, create a new urlsList
				if(!InvertedIndexMap.containsKey(keyword)){
					ArrayList<String> urlsList = new ArrayList<String>();
					urlsList.add(url);
					InvertedIndexMap.put(keyword, urlsList);
				}
				//if this keyword exists, append the url to the list
				else{
					ArrayList<String> urlsList = InvertedIndexMap.get(keyword);
					if(!urlsList.contains(url))
						urlsList.add(url);
				}
			}
		}
		this.writeToFile();
	}
	private void writeToFile() throws IOException {		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/res/InvertedIndexDataset"));					
		oos.writeObject(this.getInvertedIndexMap());					
		oos.close();
	}
	//Get the InvertedIndexMap
	public HashMap<String,ArrayList<String>> getInvertedIndexMap(){
		return InvertedIndexMap;
	}
}
