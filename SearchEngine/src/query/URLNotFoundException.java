/**
 * URL is not in forward index map exception
 * 
 * @author: ZHAO Zinan
 * @since:  14-Oct-2018
 */

package query;

public class URLNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public URLNotFoundException() {
		super();
	}
	
	public URLNotFoundException(String msg) {
		super(msg);
	}
}
