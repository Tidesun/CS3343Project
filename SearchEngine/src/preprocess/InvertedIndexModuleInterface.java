package preprocess;

import java.util.ArrayList;
import java.util.HashMap;

public interface InvertedIndexModuleInterface {
	public abstract void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap);
	public abstract HashMap<String,ArrayList<String>> getInvertedIndexMap();
}
