/**
 * URL is not in forward index map exception
 * 
 * @author: ZHAO Zinan
 * @since:  25-Oct-2018
 */

package query;

public class RankMethodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RankMethodNotFoundException() {
		super();
	}
	
	public RankMethodNotFoundException(String msg) {
		super(msg);
	}
}
