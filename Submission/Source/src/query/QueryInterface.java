package query;

import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Interface of query module.
 */
public interface QueryInterface {
	
	/**
	 * Search.
	 *
	 * @param keywords the keywords
	 * @param rankmethod the rankmethod
	 * @return the array list
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ArrayList<String> search(String keywords, String rankmethod) throws ClassNotFoundException,
			IOException;
	
	/**
	 * Search.
	 *
	 * @param keywords the keywords
	 * @return the array list
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ArrayList<String> search(String keywords) throws ClassNotFoundException, IOException;
}
