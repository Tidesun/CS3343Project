package preprocess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface InvertedIndexModuleInterface {
	public abstract void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap) throws IOException, FileNotFoundException;
	public abstract HashMap<String,ArrayList<String>> getInvertedIndexMap();
}
