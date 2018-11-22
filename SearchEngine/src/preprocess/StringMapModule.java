package preprocess;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class StringMapModule.
 */
public class StringMapModule implements StringMapModuleInterface{
	
	/** The extractor. */
	private ExtractModuleInterface extractor;
	
	/** The observer. */
	private ForwardIndexModuleInterface observer;
	
	/** The save path. */
	private String savePath;
	
	/**
	 * Instantiates a new string map module.
	 *
	 * @param extractor the extractor
	 * @param savePath the save path
	 */
	public StringMapModule(ExtractModuleInterface extractor,String savePath){
		this.extractor=extractor;
		this.savePath=savePath;
		this.observer=null;
	}
	
	/**
	 * Instantiates a new string map module.
	 *
	 * @param extractor the extractor
	 */
	public StringMapModule(ExtractModuleInterface extractor){
		this(extractor,"src/res/dataset/titleForwardIndexDataset");
	}
	
	/*
	* Title: subscribe
	* Description: 
	* @param observer 
	* @see preprocess.StringMapModuleInterface#subscribe(preprocess.ForwardIndexModuleInterface) 
	*/
	
	public void subscribe(ForwardIndexModuleInterface observer) {
		this.observer=observer;
	}
	
	/*
	* Title: generateKeywordStrMap
	* Description: 
	* @param dirPath
	* @throws IOException
	* @throws NullPointerException 
	* @see preprocess.StringMapModuleInterface#generateKeywordStrMap(java.lang.String) 
	*/
	
	public void generateKeywordStrMap(String dirPath) throws IOException,NullPointerException{
		

		List<Path> pathList = Files.walk(Paths.get(dirPath)).filter(Files::isRegularFile).collect(Collectors.toList());
		HashMap<String,String> map = new HashMap<String,String>();
		int count=0;
		for(Path path:pathList){
			String url= path.toString();
			String header= this.extractor.WebPageExtraction(path.toFile());
			map.put(url.substring(dirPath.length()), header);
		}
		if (this.observer==null) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.savePath));
			oos.writeObject(map);					
			oos.close();
			throw new NullPointerException ("ERROR: no observer subscribe to this.");
		}
		else
			this.observer.generateForwardIndexMap(map);
	}

}
