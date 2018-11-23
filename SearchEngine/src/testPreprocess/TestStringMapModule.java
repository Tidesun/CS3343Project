package testPreprocess;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import preprocess.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStringMapModule.
 */
public class TestStringMapModule {
	
	/** The stringmap. */
	StringMapModule stringmap;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		stringmap = new StringMapModule(new ExtractLinkModule());
	}
	
	/**
	 * Test generate keyword str map exception.
	 */
	// observer is null, throw null pointer exception
	@Test(expected=NullPointerException.class)
	public void testGenerateKeywordStrMapException() {
		try {
			stringmap.generateKeywordStrMap("res/html/");
		} catch (IOException e) {
			System.out.println("WARNING: IOException when testing StringMapModule.generateKeyworkMap");
			e.printStackTrace();
		}
	}
}
