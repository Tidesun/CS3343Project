package testQuery;

import static org.junit.Assert.*;
import query.CommonQuery;
import query.URLNotFoundException;
import query.URLNotinComparatorException;

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



public class TestCommonQuery {
	private CommonQuery commonQuery;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		commonQuery = new CommonQuery();
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
	
	
	//Test capitalization for the second keyword
		@Test
		public void testSearch_04() throws IOException, ClassNotFoundException {
			//1.input data
			HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
			
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("sign");
			keywords.add("LOGIN");
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
		
		
		//Test capitalization for the last keyword
		@Test
		public void testSearch_05() throws IOException, ClassNotFoundException {
			//1.input data
			HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
			
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("sign");
			keywords.add("login");
			keywords.add("PEOPLE");
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
		
		
	//test duplicated urls
	@Test
	public void testSearch_06() throws IOException, ClassNotFoundException {
		//1.input data
		HashMap<String, ArrayList<String>> forwardIndex = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> invertedIndex = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("sign");
		keywords.add("login");
		keywords.add("people");
		forwardIndex.put("www.twitter.com", keywords);
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
	public void testSearch_07() throws IOException, ClassNotFoundException {
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
		public void testSearch_08() throws IOException, ClassNotFoundException {
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
}
