/**
 * Before ranking the url, we have to pass the url - weight map to comparator, if the url is not in the map, this exception will
 * be thrown.
 * 
 * @author: ZHAO Zinan
 * @since:  14-Oct-2018
 */

package query;

public class URLNotinComparatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public URLNotinComparatorException() {
		super();
	}
	
	public URLNotinComparatorException(String msg) {
		super(msg);
	}
}
