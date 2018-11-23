package preprocess;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * StringMapModule Interface.
 */
public interface StringMapModuleInterface {
	
	/**
	 * Generate keyword str map.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public abstract void generateKeywordStrMap(String path) throws IOException;
	
	/**
	 * Subscribe.
	 *
	 * @param observer the observer
	 */
	public abstract void subscribe(ForwardIndexModuleInterface observer);

}
