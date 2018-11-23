/**
 * Before ranking the url, we have to pass the url - weight map to comparator, if the url is not in the map, this exception will
 * be thrown.
 * 
 * @author: ZHAO Zinan
 * @since:  14-Oct-2018
 */

package query;

// TODO: Auto-generated Javadoc
/**
 * Before ranking the url, we have to pass the url - weight map to comparator, if the url is not in the map, this exception will
 * be thrown.
 */
public class URLNotinComparatorException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new URL notin comparator exception.
	 */
	public URLNotinComparatorException() {
		super();
	}
	
	/**
	 * Instantiates a new URL notin comparator exception.
	 *
	 * @param msg the msg
	 */
	public URLNotinComparatorException(String msg) {
		super(msg);
	}
}
