package preprocess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class StringMapModule implements StringMapModuleInterface{
	private ExtractModuleInterface extractor;
	private ForwardIndexModuleInterface observer;
	
	public StringMapModule(ExtractModuleInterface extractor){
		this.extractor=extractor;
		this.observer=null;
	}
	
	public void subscribe(ForwardIndexModuleInterface observer) {
		this.observer=observer;
	}
	public void generateKeywordStrMap(String dirPath) throws IOException,NullPointerException{
		if (this.observer==null)
			throw new NullPointerException ("ERROR: no observer subscribe to this.");

		List<Path> pathList = Files.walk(Paths.get(dirPath)).filter(Files::isRegularFile).collect(Collectors.toList());
		HashMap<String,String> map = new HashMap<String,String>();
		for(Path path:pathList){	
			String url= path.toString();
			String header= this.extractor.WebPageExtraction(path.toFile());
			map.put(url.substring(dirPath.length()), header);
		}
		this.observer.generateForwardIndexMap(map);
	}

}
