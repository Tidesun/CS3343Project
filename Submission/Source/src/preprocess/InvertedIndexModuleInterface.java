package preprocess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * InvertedIndexModule Interface.
 */
public interface InvertedIndexModuleInterface {
	
	/**
	 * Generate inverted index map.
	 *
	 * @param ForwardIndexMap the forward index map
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	public abstract void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap) throws IOException, FileNotFoundException;
	
	/**
	 * Gets the inverted index map.
	 *
	 * @return the inverted index map
	 */
	public abstract HashMap<String,ArrayList<String>> getInvertedIndexMap();
}
