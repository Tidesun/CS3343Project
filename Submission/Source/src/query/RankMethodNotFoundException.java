/**
 * URL is not in forward index map exception
 * 
 * @author: ZHAO Zinan
 * @since:  25-Oct-2018
 */

package query;

// TODO: Auto-generated Javadoc
/**
 * The Class RankMethodNotFoundException.
 */
public class RankMethodNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new rank method not found exception.
	 */
	public RankMethodNotFoundException() {
		super();
	}
	
	/**
	 * Instantiates a new rank method not found exception.
	 *
	 * @param msg the msg
	 */
	public RankMethodNotFoundException(String msg) {
		super(msg);
	}
}
