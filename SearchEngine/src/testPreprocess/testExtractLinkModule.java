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

public class testExtractLinkModule {

	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file;
	ExtractLinkModule extractLinkModule;
   
    @Before
    public void setUp(){
        file = new File(temp.getRoot(), "mytestfile.html");
        extractLinkModule = new ExtractLinkModule();
    }

	@Test
	public void testUsingTempFolder1() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://www.cityu.edu.hk\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 

		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	

	@Test
	public void testUsingTempFolder2() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=www.cs.cityu.edu.hk\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}

		
	@Test
	public void testUsingTempFolder3() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://www.cs.cityu.edu.hk@\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	

	@Test
	public void testUsingTempFolder4() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://www.cs.cityu.edu.hk.htm\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "www.cs.cityu.edu.hk.htm "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);

		assertEquals(res,result); 
	}
		

	@Test
	public void testUsingTempFolder5() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content ="<li><a href=\"http://www.cs.cityu.edu.hk/\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "www.cs.cityu.edu.hk/ ";

		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	

	@Test
	public void testUsingTempFolder6() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://www.cs.cityu.edu.hk/.\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	

	@Test
	public void testUsingTempFolder7() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://www.cs.cityu.edu.hk.htm/#cslab\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res ="www.cs.cityu.edu.hk.htm/ "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	
}
