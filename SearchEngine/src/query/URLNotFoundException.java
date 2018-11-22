/**
 * URL is not in forward index map exception
 * 
 * @author: ZHAO Zinan
 * @since:  14-Oct-2018
 */

package query;

// TODO: Auto-generated Javadoc
/**
 * The Class URLNotFoundException.
 */
public class URLNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new URL not found exception.
	 */
	public URLNotFoundException() {
		super();
	}
	
	/**
	 * Instantiates a new URL not found exception.
	 *
	 * @param msg the msg
	 */
	public URLNotFoundException(String msg) {
		super(msg);
	}
}
