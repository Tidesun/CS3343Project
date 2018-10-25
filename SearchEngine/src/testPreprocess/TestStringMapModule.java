package testPreprocess;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import preprocess.*;

public class TestStringMapModule {
	StringMapModule stringmap;
	
	@Before
	public void setUp() {
		stringmap = new StringMapModule(new ExtractLinkModule());
	}
	
	// observer is null, throw null pointer exception
	@Test(expected=NullPointerException.class)
	public void testGenerateKeywordStrMapException() {
		try {
			stringmap.generateKeywordStrMap("src/res/html/");
		} catch (IOException e) {
			System.out.println("WARNING: IOException when testing StringMapModule.generateKeyworkMap");
			e.printStackTrace();
		}
	}
}
