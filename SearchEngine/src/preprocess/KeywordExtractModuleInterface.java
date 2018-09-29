package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface KeywordExtractModuleInterface {
	
	public abstract String WebPageExtraction(File filename) throws FileNotFoundException;

}

