package testPreprocess;

import java.lang.reflect.Field;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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


public class Integration {
	
	StringMapModuleInterface keywordMap;
	ForwardIndexModule forward;
	InvertedIndexModuleInterface observer;
	ExtractModuleInterface extractor;
	String savePath;
	TemporaryFolder temp;
	
	@Before  
	public void setUp() throws IOException { 
		temp = new TemporaryFolder();
		savePath = "mytestfile";
		keywordMap = new StringMapModule(extractor);
		forward = new ForwardIndexModule(keywordMap,savePath);
		observer = new InvertedIndexModule(forward,savePath); 
	}
	 
	@After
	public void tearDown() throws Exception { }

	

	@Test
	public void integrationtest1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
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
		

		HashMap<String,ArrayList<String>> invertedresult= observer.getInvertedIndexMap();
		
		HashMap<String,ArrayList<String>> res2 = new HashMap<String,ArrayList<String>>();
		
		ArrayList<String> result2=new ArrayList<String>();

		
		assertEquals(res,newhashmap);
		
		assertEquals(res2,invertedresult);

	}
	
	@Test
	public void integrationtest2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
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
		


		HashMap<String,ArrayList<String>> invertedresult= observer.getInvertedIndexMap();
		
		HashMap<String,ArrayList<String>> res2 = new HashMap<String,ArrayList<String>>();
		
		ArrayList<String> result2=new ArrayList<String>();
		result2.add(url1);
		res2.put(keyword1,result2);
		
		assertEquals(res,newhashmap);
		
		assertEquals(res2,invertedresult);
	}
		

	@Test
	public void integrationtest3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
				
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
		
		System.out.println(res);
		
		HashMap<String,ArrayList<String>> invertedresult= observer.getInvertedIndexMap();
		
		HashMap<String,ArrayList<String>> res2 = new HashMap<String,ArrayList<String>>();
		
		ArrayList<String> result3=new ArrayList<String>();
		ArrayList<String> result4=new ArrayList<String>();
		ArrayList<String> result5=new ArrayList<String>();
		result3.add(url1);
		result4.add(url1);
		result4.add(url2);
		result5.add(url2);
		res2.put(keyword2,result3);
		res2.put(keyword5,result4);		
		res2.put(keyword4,result5);
		
		assertEquals(res,newhashmap);
		
		assertEquals(res2,invertedresult);
		
	}
	
}
