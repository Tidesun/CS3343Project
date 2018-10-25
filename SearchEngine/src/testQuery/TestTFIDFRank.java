package testQuery;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import query.TFIDFRank;
import query.URLNotFoundException;

public class TestTFIDFRank {
	@Test
	/* Normal case test TFIDF weigh */
	public void testWeigh1() {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		 */
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		/*
		 * Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		 */
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		TFIDFRank mock=new TFIDFRank(invertedIndex,forwardIndex);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		
		double res=mock.weigh("map.google.com", mockTestKeywords);
		assertEquals(0.0,res,0);
	}
	
	@Test
	/*  Normal case test TFIDF rank  */
	public void testWeigh2() {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google"]
		 */
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		forwardIndex.put("map.google.com", mockKeywords);
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		
		
		/*
		 * Create a mock invertedIndex "google"->["google.com"] "search"->["google.com","map.google.com"]
		 */
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		invertedIndex.put("google", mockUrls);
		mockUrls.add("map.google.com");
		invertedIndex.put("search", mockUrls);
		
		TFIDFRank mock=new TFIDFRank(invertedIndex,forwardIndex);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		ArrayList<String> res=mock.rank(mockUrls, mockTestKeywords);
		assertEquals("google.com",res.get(0));
	}
	
	@Test(expected= NullPointerException.class) 
	/* If keyword not exist*/
	public void testWeigh3() {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		 */
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		/*
		 * Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		 */
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		TFIDFRank mock=new TFIDFRank(invertedIndex,forwardIndex);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		mockTestKeywords.add("github");
		mock.weigh("google.com", mockTestKeywords);
	}
	
	@Test(expected= URLNotFoundException.class) 
	/* If url not exist*/
	public void testWeigh4() {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock forwardIndex "google.com"->["google","search"] "map.google.com"->["google","search"]
		 */
		ArrayList<String> mockKeywords=new ArrayList<String>();
		mockKeywords.add("google");
		mockKeywords.add("search");
		forwardIndex.put("google.com", mockKeywords);
		forwardIndex.put("map.google.com", mockKeywords);
		
		
		/*
		 * Create a mock invertedIndex "google"->["google.com","map.google.com"] "search"->["google.com","map.google.com"]
		 */
		ArrayList<String> mockUrls=new ArrayList<String>();
		mockUrls.add("google.com");
		mockUrls.add("map.google.com");
		invertedIndex.put("google", mockUrls);
		invertedIndex.put("search", mockUrls);
		
		TFIDFRank mock=new TFIDFRank(invertedIndex,forwardIndex);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;

		assertNull(mock.weigh("github.com", mockTestKeywords));
	}

}
