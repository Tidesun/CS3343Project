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

public class testExtractBodyModule {

	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file;
	ExtractBodyModule extractBodyModule;

    @Before
    public void setUp(){
        file = new File(temp.getRoot(), "mytestfile.html");
        extractBodyModule = new ExtractBodyModule();
    }
    
	@Test
	public void testUsingTempFolder1() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<title>This is title.</title>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is title\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	@Test
	public void testUsingTempFolder2() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<p>This is paragraph.</p>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is paragraph\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	 
	@Test
	public void testUsingTempFolder3() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<style>This is style.</style>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is style\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	@Test
	public void testUsingTempFolder4() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<h1>This is head.</h1>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is head\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	@Test
	public void testUsingTempFolder5() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<a>This is link.</a>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is link\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	@Test
	public void testUsingTempFolder6() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		String content = "<th>This is tablehead.</th>";
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		String res = "This is tablehead\n"; 
		String result = extractBodyModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
}
