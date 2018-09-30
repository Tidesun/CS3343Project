package preprocess;

import java.io.File;
import java.io.FileNotFoundException;

public interface ExtractModuleInterface {
	
	public abstract String WebPageExtraction(File filename) throws FileNotFoundException;

}

