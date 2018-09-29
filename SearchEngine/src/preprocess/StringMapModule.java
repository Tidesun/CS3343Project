package preprocess;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class StringMapModule implements StringMapModuleInterface{
	private ExtractModuleInterface extractor;
	private ForwardIndexModuleInterface observer;
	
	StringMapModule(){
		this.extractor=new ExtractBodyModule();
		this.observer=null;
	}
	
	public void subscribe(ForwardIndexModuleInterface observer) {
		this.observer=observer;
	}
	public void generateKeywordStrMap(String path) throws IOException{
		
		File[] fileList = new File(path).listFiles();	
		
		HashMap<String,String> map = new HashMap<String,String>();
		for(File file:fileList){	
			String url= file.toString();
			String header= this.extractor.WebPageExtraction(file);
			map.put(url, header);
		}
		this.observer.generateForwardIndexMap(map);
	}

}
