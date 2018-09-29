package preprocess;

import java.io.File;
import java.io.FileNotFoundException;

public interface KeywordExtractModuleInterface {
	
	public abstract String WebPageExtraction(File filename) throws FileNotFoundException;

}

