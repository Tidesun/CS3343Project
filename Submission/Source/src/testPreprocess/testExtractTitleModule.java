package testPreprocess;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import preprocess.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class testExtractTitleModule.
 */
public class testExtractTitleModule {

	/** The temp. */
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	
	/** The file. */
	File file;
	
	/** The extract title module. */
	ExtractTitleModule extractTitleModule;
   
    /**
     * Sets the up.
     */
    @Before
    public void setUp(){
        file = new File(temp.getRoot(), "mytestfile.html");
        extractTitleModule = new ExtractTitleModule();
    }
 
	/**
	 * Test using temp folder 1.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder1() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<title>This is title</title>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "This is title"; 

		// what actually it is
		String result = extractTitleModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	

	/**
	 * Test using temp folder 2.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder2() throws IOException {
		File createdFile1= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<title>This is title<title>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile1));
		ps.println(content); 
		// what I think it should be
		String res = null; 

		// what actually it is
		String result = extractTitleModule.WebPageExtraction(createdFile1);
		assertEquals(res,result); 
	}
}