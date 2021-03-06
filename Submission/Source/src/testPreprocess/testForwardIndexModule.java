package testPreprocess;

import java.lang.reflect.Field;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.lang.reflect.Field;
import preprocess.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class testForwardIndexModule.
 */
public class testForwardIndexModule {
	
	/** The keyword map. */
	StringMapModuleInterface keywordMap;
	
	/** The forward. */
	ForwardIndexModule forward;
	
	/** The observer. */
	InvertedIndexModuleInterface observer;
	
	/** The extractor. */
	ExtractModuleInterface extractor;
	
	/** The save path. */
	String savePath;
	
	/** The temp. */
	TemporaryFolder temp;
	
	/**
	 * Sets the up.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Before 
	public void setUp() throws IOException { 
		temp = new TemporaryFolder();
		savePath = "mytestfile";
		keywordMap = new StringMapModule(extractor);
		forward = new ForwardIndexModule(keywordMap,savePath);
		observer = new InvertedIndexModule(forward,savePath); 
	}
	
	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception { }
	
	/**
	 * Test secribe.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	public void testSecribe() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		//get private variable observer
		Class<?> class1 = forward.getClass();
		Field field = class1.getDeclaredField("observer");
		field.setAccessible(true);
		forward.subscribe(observer);
		InvertedIndexModuleInterface observer1 = (InvertedIndexModuleInterface) field.get(forward);
		
		assertEquals(observer,observer1);
	}
	
	/**
	 * Test create the stopwords list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCreateTheStopwordsList() throws Exception{
		
		//get control variable
		File sFile = new File("res/stopwords.txt");
		ArrayList<String> stopwords = new ArrayList<String>();
		
		//get private variable stopwords_list
		Class<?> class2 = forward.getClass();
		Field field = class2.getDeclaredField("stopwords_list");
		field.setAccessible(true);
		forward.createTheStopwordsList();
		ArrayList<String> stopwords_list = (ArrayList<String>) field.get(forward);
		
		Scanner sc;
		sc = new Scanner(sFile);
		while(sc.hasNextLine()) {
			String theword = sc.nextLine();
			stopwords.add(theword);
		}
		sc.close();
		
		
		assertEquals(stopwords, stopwords_list);
	}
		
	/**
	 * Test generate forward index map 1.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*
	 * 0 vs 0
	 */
	@Test
	public void testGenerateForwardIndexMap1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
		class stubInvertedIndexModuleInterface implements InvertedIndexModuleInterface{
			public void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap) throws IOException, FileNotFoundException{
			}
			public HashMap<String,ArrayList<String>> getInvertedIndexMap(){
				return null;
			} 
		}
		observer = new stubInvertedIndexModuleInterface();
		 		
		//get method result
		HashMap<String, String> oMap = new HashMap<String,String>();
		Class<?> class3 = forward.getClass();
		Field field = class3.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
		forward.generateForwardIndexMap(oMap);
		HashMap<String, ArrayList<String>> newhashmap = (HashMap<String, ArrayList<String>>) field.get(forward);
		 
		//get control result
		ArrayList<String> result=new ArrayList<String>();
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>(); 
		
		assertEquals(res,newhashmap);

	}
	
	
	/**
	 * Test generate forward index map 2.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*
	 * 1 vs 1
	 */
	@Test
	public void testGenerateForwardIndexMap2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
		class stubInvertedIndexModuleInterface implements InvertedIndexModuleInterface{
			public void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap) throws IOException, FileNotFoundException{
			}
			public HashMap<String,ArrayList<String>> getInvertedIndexMap(){
				return null;
			} 
		}
		observer = new stubInvertedIndexModuleInterface();
		//build environment
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "github";
		String keyword2 = new String();
		keyword2 = "github";
		
		//get method result
		HashMap<String, String> oMap = new HashMap<String,String>();
		oMap.put(url1, keyword1);
		Class<?> class3 = forward.getClass();
		Field field = class3.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
		forward.generateForwardIndexMap(oMap);
		HashMap<String, ArrayList<String>> newhashmap = (HashMap<String, ArrayList<String>>) field.get(forward);
		 
		//get control result
		ArrayList<String> result=new ArrayList<String>();
		result.add(keyword2);
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>(); 
		res.put(url1, result);
		
		assertEquals(res,newhashmap);

	}
		
	
	
	/**
	 * Test generate forward index map 3.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*
	 * more(2) vs more(2)
	 */
	@Test
	public void testGenerateForwardIndexMap3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
		class stubInvertedIndexModuleInterface implements InvertedIndexModuleInterface{
			public void generateInvertedIndexMap(HashMap<String,ArrayList<String>> ForwardIndexMap) throws IOException, FileNotFoundException{
			}
			public HashMap<String,ArrayList<String>> getInvertedIndexMap(){
				return null;
			} 
		}
		observer = new stubInvertedIndexModuleInterface();
		
		//build environment
		String url1=new String();
		url1 = "github.com";
		String url2=new String();
		url2 = "facebook.com";
		String keyword1 = new String();
		keyword1 = "github website";
		String keyword2 = new String();
		keyword2 = "github";
		String keyword3 = new String();
		keyword3 = "facebook website";
		String keyword4 = new String();
		keyword4 = "facebook";
		String keyword5 = new String();
		keyword5 = "website";
		
		//get method result
		HashMap<String, String> oMap = new HashMap<String,String>();
		oMap.put(url1, keyword1);
		oMap.put(url2, keyword3);
		Class<?> class3 = forward.getClass();
		Field field = class3.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
		forward.generateForwardIndexMap(oMap);
		HashMap<String, ArrayList<String>> newhashmap = (HashMap<String, ArrayList<String>>) field.get(forward);
		 
		//get control result
		ArrayList<String> result1=new ArrayList<String>();
		result1.add(keyword2);
		result1.add(keyword5);
		ArrayList<String> result2=new ArrayList<String>();
		result2.add(keyword4);
		result2.add(keyword5);
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>(); 
		res.put(url1, result1);
		res.put(url2, result2);
		
		assertEquals(res,newhashmap);

	}
	
	
	

	/**
	 * Test get keyword from origin hash map 0.
	 */
	@Test
	public void testGetKeywordFromOriginHashMap0() {
	
		//build environment
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "";
		forward.createArrayListOfURL(url1);
		forward.addKeywordsToTheHashMap(url1,keyword1);
		forward.createTheStopwordsList();
		
		//get method result
		String kwstring = new String();
		kwstring = keyword1;
		ArrayList<String> kwarr = forward.getKeywordFromOriginHashMap(kwstring);
		 
		//get control result
		ArrayList<String> res=new ArrayList<String>();
		
		assertEquals(res,kwarr);
		
	}
	
	
	
	/**
	 * Test get keyword from origin hash map 1.
	 */
	/*
	 * kwstr=0
	 */
	@Test
	public void testGetKeywordFromOriginHashMap1() {
	
		//build environment
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "  ";
		forward.createArrayListOfURL(url1);
		forward.addKeywordsToTheHashMap(url1,keyword1);
		forward.createTheStopwordsList();
		
		//get method result
		String kwstring = new String();
		kwstring = keyword1;
		ArrayList<String> kwarr = forward.getKeywordFromOriginHashMap(kwstring);
		 
		//get control result
		ArrayList<String> res=new ArrayList<String>();
		
		assertEquals(res,kwarr);
		
	}
	
	/**
	 * Test get keyword from origin hash map 2.
	 */
	/*
	 * kwstr=1 T T
	 */
	@Test
	public void testGetKeywordFromOriginHashMap2() {
	
		//build environment
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "github";
		forward.createArrayListOfURL(url1);
		forward.addKeywordsToTheHashMap(url1,keyword1);
		forward.createTheStopwordsList();
		
		//get method result
		String kwstring = new String();
		kwstring = "github";
		ArrayList<String> kwarr = forward.getKeywordFromOriginHashMap(kwstring);
		
		//get control result
		ArrayList<String> res=new ArrayList<String>();
		res.add(keyword1);
		
		assertEquals(res,kwarr);
		
	}	
	
	/**
	 * Test get keyword from origin hash map 3.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	/*
	 * kwstr=1 T F
	 */
	@Test
	public void testGetKeywordFromOriginHashMap3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	
		//build environment
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "is";
		forward.createArrayListOfURL(url1);
		forward.addKeywordsToTheHashMap(url1,keyword1);
		forward.createTheStopwordsList();
		
		//get method result
		String kwstring = new String();
		kwstring = keyword1; 
		ArrayList<String> kwarr = forward.getKeywordFromOriginHashMap(kwstring);
		
		//get control result
		ArrayList<String> res=new ArrayList<String>();
		
		assertEquals(res,kwarr);
		
	}
	

	
	/**
	 * Test add keywords to the hash map 1.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	public void testAddKeywordsToTheHashMap1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
				
		//get private variable ForwardIndexMap
		Class<?> class5 = forward.getClass();
		Field field = class5.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
				
		//get method result 
		//suppose url exists in the hashmap
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "github";
		forward.createArrayListOfURL(url1);
		forward.addKeywordsToTheHashMap(url1,keyword1);
		HashMap<String, ArrayList<String>> result = (HashMap<String, ArrayList<String>>) field.get(forward);
		
		//get control result
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		ArrayList<String> keywordlist = new ArrayList<String>();
		keywordlist.add(keyword1);
		res.put(url1,keywordlist);
		
		assertEquals(res,result);
	}
	
	/**
	 * Test add keywords to the hash map 2.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	public void testAddKeywordsToTheHashMap2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
				
		//get private variable ForwardIndexMap
		Class<?> class5 = forward.getClass();
		Field field = class5.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
				
		//get method result 
		//suppose url does not exist in the hashmap
		String url1=new String();
		url1 = "github.com";
		String keyword1 = new String();
		keyword1 = "github";
		forward.addKeywordsToTheHashMap(url1,keyword1);
		HashMap<String, ArrayList<String>> result = (HashMap<String, ArrayList<String>>) field.get(forward);
		
		//get control result
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		ArrayList<String> keywordlist = new ArrayList<String>();
		keywordlist.add(keyword1);
		res.put(url1,keywordlist);
		
		assertEquals(res,result);
	}
	
	
	/**
	 * Test create array list of URL.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SecurityException the security exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	public void testCreateArrayListOfURL() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	
		//get private variable ForwardIndexMap
		Class<?> class6 = forward.getClass();
		Field field = class6.getDeclaredField("ForwardIndexMap");
		field.setAccessible(true);
		
		//get method result 
		String url=new String();
		url = "github.com";
		forward.createArrayListOfURL(url);
		HashMap<String, ArrayList<String>> result = (HashMap<String, ArrayList<String>>) field.get(forward);
		
		//get control result
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		ArrayList<String> arrlist = new ArrayList<String>();
		res.put(url,arrlist);
 
		assertEquals(res,result);
	}
	
	
	
}
