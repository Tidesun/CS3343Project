package preprocess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * ForwardIndexModule interface.
 */
public interface ForwardIndexModuleInterface {
	
	/**
	 * Generate forward index map.
	 *
	 * @param origin_map the origin map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public abstract void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException;
	
	/**
	 * Subscribe.
	 *
	 * @param observer the observer
	 */
	public abstract void subscribe(InvertedIndexModuleInterface observer);
	
	/**
	 * Gets the forward index map.
	 *
	 * @return the forward index map
	 */
	public abstract HashMap<String,ArrayList<String>> getForwardIndexMap();
}
