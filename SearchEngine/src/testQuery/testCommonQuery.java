package testQuery;

import static org.junit.Assert.*;
import query.CommonQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class testCommonQuery {
	private CommonQuery commonQuery;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testSearch_01() throws IOException, ClassNotFoundException {

		ArrayList<String> keywords = new ArrayList<String>();
		ArrayList<String> expectedURLs = new ArrayList<>();
		
		commonQuery = new CommonQuery();
		
		ArrayList<String> realURLs = commonQuery.search(keywords);
		assertEquals(expectedURLs, realURLs);
	}
	
	@Test
	public void testSearch_02() throws IOException, ClassNotFoundException {
		//1.input data
		HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> test1_flist = new ArrayList<String>();
		test1_flist.add("search");
		test1_flist.add("google");
		forwardIndex.put("www.google.com", test1_flist);
		
		ArrayList<String> test1_inlist = new ArrayList<String>();
		test1_inlist.add("www.google.com");
		invertedIndex.put("search", test1_inlist);
		invertedIndex.put("google", test1_inlist);
		
		File tempForwardFile = File.createTempFile("src/res/tempforwardfile", ".txt");
		File tempInvertedFile = File.createTempFile("src/res/tempinvertedfile", ".txt");
		tempForwardFile.deleteOnExit();
		tempInvertedFile.deleteOnExit();
		
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("src/res/tempforwardfile"));					
		oos1.writeObject(forwardIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("src/res/tempinvertedfile"));					
		oos2.writeObject(invertedIndex);					
		oos2.close();
		
		commonQuery = new CommonQuery("src/res/tempinvertedfile","src/res/tempforwardfile");
		
		//2.test execution
		ArrayList<String> realURLs = commonQuery.search("search google");
		//3.test result
		
		assertEquals(test1_inlist, realURLs);
	}
	
	@Test
	public void testSearch_03() throws IOException, ClassNotFoundException {
		//1.input data
		HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> test1_flist = new ArrayList<String>();
		test1_flist.add("search");
		test1_flist.add("google");
		forwardIndex.put("www.google.com", test1_flist);
		
		ArrayList<String> test2_flist = new ArrayList<String>();
		test2_flist.add("search");
		test2_flist.add("yahoo");
		forwardIndex.put("www.yahoo.com", test2_flist);
		
		ArrayList<String> test1_inlist = new ArrayList<String>();
		test1_inlist.add("www.google.com");
		test1_inlist.add("www.yahoo.com");
		ArrayList<String> test2_inlist = new ArrayList<String>();
		test2_inlist.add("www.google.com");
		ArrayList<String> test3_inlist = new ArrayList<String>();
		test3_inlist.add("www.yahoo.com");
		
		invertedIndex.put("search", test1_inlist);
		invertedIndex.put("google", test2_inlist);
		invertedIndex.put("yahoo", test3_inlist);
		
		File tempForwardFile = File.createTempFile("src/res/tempforwardfile", ".txt");
		File tempInvertedFile = File.createTempFile("src/res/tempinvertedfile", ".txt");
		tempForwardFile.deleteOnExit();
		tempInvertedFile.deleteOnExit();
		
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("src/res/tempforwardfile"));					
		oos1.writeObject(forwardIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("src/res/tempinvertedfile"));					
		oos2.writeObject(invertedIndex);					
		oos2.close();
		
		commonQuery = new CommonQuery("src/res/tempinvertedfile","src/res/tempforwardfile");
		
		//2.expected result
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("www.yahoo.com");
		expectedURLs.add("www.google.com");
		
		//3.test execution
		ArrayList<String> realURLs = commonQuery.search("search");
		
		//4.test result
		assertEquals(expectedURLs, realURLs);
	}
	
	
	/*
	@Test
	public void testquerySingleKeyword_01() throws ClassNotFoundException, IOException {
		commonQuery = new CommonQuery();
		
		String singlekeyword = null;
		ArrayList<String> expectedURLs = new ArrayList<>();
		
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		assertEquals(expectedURLs, realURLs);
			
	}
	*/
	
	@Test
	public void testquerySingleKeyword_02() throws ClassNotFoundException, IOException {
		commonQuery = new CommonQuery();
		
		String singlekeyword = "airbnb";
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("src\\res\\html\\www.airbnb.com.html");
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		
		assertEquals(expectedURLs, realURLs);
			
	}
	
	@Test
	public void testquerySingleKeyword_03() throws ClassNotFoundException, IOException {
		commonQuery = new CommonQuery();
		
		String singlekeyword = "sign";
		
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("src\\res\\html\\www.imdb.com.html");
		expectedURLs.add("src\\res\\html\\www.microsoft.com.html");
		expectedURLs.add("src\\res\\html\\www.stackoverflow.com.html");
		expectedURLs.add("src\\res\\html\\GitHub.html");
		expectedURLs.add("src\\res\\html\\www.pexels.com.html");
		expectedURLs.add("src\\res\\html\\www.twitter.com.html");
		expectedURLs.add("src\\res\\html\\www.facebook.com.html");
		expectedURLs.add("src\\res\\html\\www.canva.com.html");
		expectedURLs.add("src\\res\\html\\www.amazon.com.html");
		
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		
		assertEquals(expectedURLs, realURLs);
	}
}
