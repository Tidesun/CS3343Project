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
 * The Class TestExtractBodyModule.
 */
public class TestExtractBodyModule {

	/** The temp. */
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	
	/** The file. */
	File file;
	
	/** The extract body module. */
	ExtractBodyModule extractBodyModule;

    /**
     * Sets the up.
     */
    @Before
    public void setUp(){
        file = new File(temp.getRoot(), "mytestfile.html");
        extractBodyModule = new ExtractBodyModule();
    }
    
	/**
	 * Test using temp folder 1.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder1() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<title>This is title.</title>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.print(content); 
		String res = "This is title"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	/**
	 * Test using temp folder 2.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder2() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<p>This is paragraph.</p>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is paragraph"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	 

	/**
	 * Test using temp folder 3.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder3() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<h1>This is head.</h1>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is head"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	/**
	 * Test using temp folder 4.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder4() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<a>This is link.</a>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is link"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	/**
	 * Test using temp folder 5.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUsingTempFolder5() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<th>This is tablehead.</th>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is tablehead"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
}
