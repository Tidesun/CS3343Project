package testQuery;

import static org.junit.Assert.*;
import query.CommonQuery;
import query.TFIDFRank;
import query.URLNotFoundException;
import query.URLNotinComparatorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;



public class TestCommonQuery {
	private CommonQuery commonQuery;
	
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file1;
	File file2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		commonQuery = new CommonQuery();
		file1 = new File(temp.getRoot(), "InvertedIndexDataset");
		file2 = new File(temp.getRoot(), "ForwardIndexDataset");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	//test empty dataset
	@Test
	public void testSearch_01() throws IOException, URLNotFoundException, URLNotinComparatorException, ClassNotFoundException {

		ArrayList<String> keywords = new ArrayList<String>();
		ArrayList<String> expectedURLs = new ArrayList<>();

		ArrayList<String> realURLs = commonQuery.search(keywords);
		assertEquals(expectedURLs, realURLs);
	}
	
	
	//common case
	@Test
	public void testSearch_02() throws IOException, ClassNotFoundException {
		//1.input data
		HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("sign");
		keywords.add("login");
		keywords.add("people");
		forwardIndex.put("www.twitter.com", keywords);
		
		ArrayList<String> url = new ArrayList<String>();
		url.add("www.twitter.com");
		invertedIndex.put("sign", url);
		invertedIndex.put("login", url);
		invertedIndex.put("people", url);
		
		File directory = new File(".");
        String path = directory.getCanonicalPath();
        String path1 = path+"\\ForwardIndexDataset";        
        String path2 = path+"\\InvertedIndexDataset";
        
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));					
		oos1.writeObject(forwardIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));					
		oos2.writeObject(invertedIndex);					
		oos2.close();
		
		commonQuery = new CommonQuery(path2,path1);
		ArrayList<String> expectedurl = new ArrayList<String>();
		expectedurl.add("www.twitter.com");
		
		//2.test execution
		ArrayList<String> searchkeywords = new ArrayList<String>();
		searchkeywords.add("sign");
		searchkeywords.add("login");
		searchkeywords.add("people");
		ArrayList<String> realURLsArrlst = commonQuery.search(searchkeywords,"tfidf",path2,path1);
		
		//3.test result
		assertEquals(expectedurl, realURLsArrlst);
	}
	
	//Test capitalization for the first keyword
	@Test
	public void testSearch_03() throws IOException, ClassNotFoundException {
		//1.input data
		HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("SIGN");
		keywords.add("login");
		keywords.add("people");
		forwardIndex.put("www.twitter.com", keywords);
		
		ArrayList<String> url = new ArrayList<String>();
		url.add("www.twitter.com");
		invertedIndex.put("sign", url);
		invertedIndex.put("login", url);
		invertedIndex.put("people", url);
		
		File directory = new File(".");
        String path = directory.getCanonicalPath();
        String path1 = path+"\\ForwardIndexDataset";        
        String path2 = path+"\\InvertedIndexDataset";
        
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));					
		oos1.writeObject(forwardIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));					
		oos2.writeObject(invertedIndex);					
		oos2.close();
		
		commonQuery = new CommonQuery(path2,path1);
		
		ArrayList<String> expectedurl = new ArrayList<String>();
		expectedurl.add("www.twitter.com");
		
		//2.test execution
		ArrayList<String> searchkeywords = new ArrayList<String>();
		searchkeywords.add("sign");
		searchkeywords.add("login");
		searchkeywords.add("people");
		ArrayList<String> realURLsArrlst = commonQuery.search(searchkeywords,"tfidf",path2,path1);
		
		//3.test result
		
		assertEquals(expectedurl, realURLsArrlst);
	}
	
	//test ranked urls with single keyword
	@Test
	public void testSearch_04() throws IOException, ClassNotFoundException {
		//1.input data
			HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
			
			ArrayList<String> keywords1 = new ArrayList<String>();
			keywords1.add("login");
			forwardIndex.put("www.twitter.com", keywords1);
			
			ArrayList<String> keywords2 = new ArrayList<String>();
			keywords2.add("search");
			keywords2.add("login");
			forwardIndex.put("www.google.com", keywords2);
			
			ArrayList<String> url1 = new ArrayList<String>();
			url1.add("www.twitter.com");
			url1.add("www.google.com");
			ArrayList<String> url2 = new ArrayList<String>();
			url2.add("www.google.com");
			
			invertedIndex.put("login", url1);
			invertedIndex.put("search", url2);

			File directory = new File(".");
	        String path = directory.getCanonicalPath();
	        String path1 = path+"\\ForwardIndexDataset";        
	        String path2 = path+"\\InvertedIndexDataset";
	        
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));					
			oos1.writeObject(forwardIndex);					
			oos1.close();
			
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));					
			oos2.writeObject(invertedIndex);					
			oos2.close();
			
			commonQuery = new CommonQuery(path2,path1);
			
			ArrayList<String> expectedURLs = new ArrayList<>();
			
			expectedURLs.add("www.google.com");
			expectedURLs.add("www.twitter.com");
			
			//2.test execution
			ArrayList<String> searchkeywords = new ArrayList<String>();
			searchkeywords.add("login");
			ArrayList<String> realURLsArrlst = commonQuery.search(searchkeywords,"tfidf",path2,path1);
			
			//3.test result
			assertEquals(expectedURLs, realURLsArrlst);
	}
	
	//test ranked urls with multiple keywords
	@Test
	public void testSearch_05() throws IOException, ClassNotFoundException {
		//1.input data
			HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
			
			ArrayList<String> keywords1 = new ArrayList<String>();
			keywords1.add("login");
			keywords1.add("sign");
			forwardIndex.put("www.twitter.com", keywords1);
			
			ArrayList<String> keywords2 = new ArrayList<String>();
			keywords2.add("search");
			keywords2.add("login");
			keywords2.add("sign");
			forwardIndex.put("www.google.com", keywords2);
			
			ArrayList<String> keywords3 = new ArrayList<String>();
			keywords3.add("airbnb");
			keywords3.add("trip");
			keywords3.add("login");
			keywords3.add("sign");
			forwardIndex.put("www.airbnb.com", keywords1);
			
			
			ArrayList<String> url1 = new ArrayList<String>();
			url1.add("www.airbnb.com");
			
			ArrayList<String> url2 = new ArrayList<String>();
			url2.add("www.airbnb.com");

			ArrayList<String> url3 = new ArrayList<String>();
			url3.add("www.google.com");
			
			ArrayList<String> url4 = new ArrayList<String>();
			url4.add("www.twitter.com");
			url4.add("www.google.com");
			url4.add("www.airbnb.com");
			
			ArrayList<String> url5 = new ArrayList<String>();
			url5.add("www.twitter.com");
			url5.add("www.google.com");
			url5.add("www.airbnb.com");
			
			invertedIndex.put("airbnb", url1);
			invertedIndex.put("trip", url2);
			invertedIndex.put("search", url3);
			invertedIndex.put("login", url4);
			invertedIndex.put("sign", url5);

			File directory = new File(".");
	        String path = directory.getCanonicalPath();
	        String path1 = path+"\\ForwardIndexDataset";        
	        String path2 = path+"\\InvertedIndexDataset";
	        
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));					
			oos1.writeObject(forwardIndex);					
			oos1.close();
			
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));					
			oos2.writeObject(invertedIndex);					
			oos2.close();
			
			commonQuery = new CommonQuery(path2,path1);
			
			ArrayList<String> expectedURLs = new ArrayList<>();
			
			expectedURLs.add("www.google.com");
			expectedURLs.add("www.twitter.com");
			expectedURLs.add("www.airbnb.com");
			
			
			//2.test execution
			ArrayList<String> searchkeywords = new ArrayList<String>();
			searchkeywords.add("login");
			searchkeywords.add("sign");
			ArrayList<String> realURLsArrlst = commonQuery.search(searchkeywords,"tfidf",path2,path1);
			
			//3.test result
			assertEquals(expectedURLs, realURLsArrlst);
	}
	
	//test querySingleKeyword method
	@Test
	public void testSearch_06() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		Method method = CommonQuery.class.getDeclaredMethod("querySingleKeyword", String.class);
		method.setAccessible(true);
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = (ArrayList<String>) method.invoke(commonQuery, "google");
		assertEquals(exp_res, res);
	}
	
	//test search method - tfidf
	@Test
	public void testSearch_07() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> kw_list = new ArrayList<String>();
		kw_list.add("google");
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search(kw_list ,"tfidf", path1, path2);
		assertEquals(exp_res, res);
	}
	
	//test search method - page rank
	@Test
	public void testSearch_08() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> kw_list = new ArrayList<String>();
		kw_list.add("google");
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search(kw_list ,"pagerank", path1, path2);
		assertEquals(exp_res, res);
	}
	
	//test search method - search(ArrayList<String> keywords, String rankMethod) 
	@Test
	public void testSearch_09() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> kw_list = new ArrayList<String>();
		kw_list.add("google");
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search(kw_list ,"tfidf");
		assertEquals(exp_res, res);
	}
	
	//test search method - search(String keywords, String rankMethod)
	@Test
	public void testSearch_12() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search("google" ,"tfidf");
		assertEquals(exp_res, res);
	}
	
	//test search method - search(ArrayList<String> keywords)
	@Test
	public void testSearch_13() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> kw_list = new ArrayList<String>();
		kw_list.add("google");
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search(kw_list);
		assertEquals(exp_res, res);
	}
	
	//test search method - search(String keywords)
	@Test
	public void testSearch_14() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		
		//Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		//Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		//Write inverted and forward hashmap into temp files
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		//==================== test part ====================
		commonQuery = new CommonQuery(path1,path2);
		
		ArrayList<String> exp_res = new ArrayList<String>();
		exp_res.add("google.com");
		exp_res.add("map.google.com");
		
		ArrayList<String> res = commonQuery.search("google search");
		assertEquals(exp_res, res);
	}
	
}
