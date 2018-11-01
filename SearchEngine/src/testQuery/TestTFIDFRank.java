package testQuery;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import query.TFIDFRank;
import query.URLNotFoundException;

public class TestTFIDFRank {
	
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file1;
	File file2;
	
	@Before
	public void setUp(){
		 file1 = new File(temp.getRoot(), "InvertedIndexDataset");
		 file2 = new File(temp.getRoot(), "ForwardIndexDataset");
	}
	
	@Test
	/* Normal case test TFIDF weigh */
	public void testWeigh1() throws FileNotFoundException, ClassNotFoundException, IOException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock=new TFIDFRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		
		double res=mock.weigh("map.google.com", mockTestKeywords);
		assertEquals(0.0,res,0);
	} 

	@Test
	/*  Normal case test TFIDF rank  */
	public void testWeigh2() throws FileNotFoundException, IOException, ClassNotFoundException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock=new TFIDFRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		ArrayList<String> res=mock.rank(mockUrls, mockTestKeywords);
		assertEquals("google.com",res.get(0));
	}
	
	@Test(expected= NullPointerException.class) 
	/* If keyword not exist*/
	public void testWeigh3() throws FileNotFoundException, IOException, ClassNotFoundException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock=new TFIDFRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;
		mockTestKeywords.add("github");
		mock.weigh("google.com", mockTestKeywords);
	}
	
	@Test(expected= URLNotFoundException.class) 
	/* If url not exist*/
	public void testWeigh4() throws FileNotFoundException, IOException, ClassNotFoundException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock=new TFIDFRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords=mockKeywords;

		assertNull(mock.weigh("github.com", mockTestKeywords));
	}

	@Test
	/* test idfweigh */
	public void testWeigh5() throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock = new TFIDFRank(path1,path2);
		Method method = TFIDFRank.class.getDeclaredMethod("idfWeigh", ArrayList.class);
		method.setAccessible(true);
		
		HashMap<String, Double> exp_res = new HashMap<String, Double>();
		exp_res.put("google", 0.0);
		exp_res.put("search", 0.0);
		
		HashMap<String, Double> res = (HashMap<String, Double>) method.invoke(mock, mockKeywords);
		assertEquals(exp_res, res);
	}
	
	@Test
	/* test tfweigh */
	public void testWeigh6() throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
		
		/*
		 * Write inverted and forward hashmap into temp files 
		 */
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(path1));
		oos1.writeObject(invertedIndex);					
		oos1.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(path2));
		oos2.writeObject(forwardIndex);					
		oos2.close();
		
		/*
		 * test part
		 */ 		
		TFIDFRank mock = new TFIDFRank(path1,path2);
		Method method = TFIDFRank.class.getDeclaredMethod("tfWeigh", String.class, ArrayList.class);
		method.setAccessible(true);
		
		HashMap<String, Double> exp_res = new HashMap<String, Double>();
		exp_res.put("google", 0.5);
		exp_res.put("search", 0.5);
		
		HashMap<String, Double> res = (HashMap<String, Double>) method.invoke(mock, "google.com", mockKeywords);
		assertEquals(exp_res, res);
	}
	
}
