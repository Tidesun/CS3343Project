/**   
* @Title: TestPageRank.java 
* @Package testQuery 
* @Description: TODO
* @version  
*/
package testQuery;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import query.PageRank;
import query.TFIDFRank;
/** 
* @ClassName: TestPageRank 
* @Description: TODO 
*/
public class TestPageRank {
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file1;
	File file2;
	
	@Before
	public void setUp(){
		 file1 = new File(temp.getRoot(), "InvertedIndexDataset");
		 file2 = new File(temp.getRoot(), "ForwardIndexDataset");
	}
	@Before
	public void defaultIndex() throws IOException {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock linkForwardIndex "google.com"->[], "map.google.com"->["google.com"]
		 */
		ArrayList<String> mockOutLinks1=new ArrayList<String>();
		ArrayList<String> mockOutLinks2=new ArrayList<String>();
		mockOutLinks2.add("google.com");
		forwardIndex.put("google.com", mockOutLinks1);
		forwardIndex.put("map.google.com", mockOutLinks2);
		
		/*
		 * Create a mock linkInvertedIndex "google.com"->["map.google.com"], "map.google.com"->[]
		 */
		ArrayList<String> mockInLinks1=new ArrayList<String>();
		ArrayList<String> mockInLinks2=new ArrayList<String>();
		mockInLinks1.add("map.google.com");
		invertedIndex.put("google.com", mockInLinks1);
		invertedIndex.put("map.google.com", mockInLinks2);
		
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
	}
	@Test
	public void testWeigh1() throws FileNotFoundException, IOException, ClassNotFoundException {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock linkForwardIndex "google.com"->["map.google.com"], "map.google.com"->["google.com"]
		 */
		ArrayList<String> mockOutLinks1=new ArrayList<String>();
		ArrayList<String> mockOutLinks2=new ArrayList<String>();
		mockOutLinks1.add("map.google.com");
		mockOutLinks2.add("google.com");
		forwardIndex.put("google.com", mockOutLinks1);
		forwardIndex.put("map.google.com", mockOutLinks2);
		
		/*
		 * Create a mock linkInvertedIndex "google.com"->["map.google.com"], "map.google.com"->["google.com"]
		 */
		ArrayList<String> mockInLinks1=new ArrayList<String>();
		ArrayList<String> mockInLinks2=new ArrayList<String>();
		mockInLinks1.add("map.google.com");
		mockInLinks1.add("google.com");
		invertedIndex.put("google.com", mockInLinks1);
		invertedIndex.put("map.google.com", mockInLinks1);
		
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
		PageRank mock=new PageRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords.add("google");
		
		double res=mock.weigh("map.google.com", mockTestKeywords)-mock.weigh("google.com", mockTestKeywords);
		assertEquals(0.0,res,0);
			
	}
	@Test
	public void testWeigh2() throws FileNotFoundException, IOException, ClassNotFoundException {
		HashMap<String, ArrayList<String>> invertedIndex=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> forwardIndex=new HashMap<String, ArrayList<String>>();
		/*
		 * Create a mock linkForwardIndex "google.com"->[], "map.google.com"->["google.com"]
		 */
		ArrayList<String> mockOutLinks1=new ArrayList<String>();
		ArrayList<String> mockOutLinks2=new ArrayList<String>();
		mockOutLinks2.add("google.com");
		forwardIndex.put("google.com", mockOutLinks1);
		forwardIndex.put("map.google.com", mockOutLinks2);
		
		/*
		 * Create a mock linkInvertedIndex "google.com"->["map.google.com"], "map.google.com"->[]
		 */
		ArrayList<String> mockInLinks1=new ArrayList<String>();
		ArrayList<String> mockInLinks2=new ArrayList<String>();
		mockInLinks1.add("map.google.com");
		invertedIndex.put("google.com", mockInLinks1);
		invertedIndex.put("map.google.com", mockInLinks2);
		
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
		PageRank mock=new PageRank(path1,path2);
		ArrayList<String> mockTestKeywords=new ArrayList<String>();
		mockTestKeywords.add("google");
		
		boolean res=mock.weigh("google.com", mockTestKeywords)-mock.weigh("map.google.com", mockTestKeywords)>0;
		assertEquals(true,res);
			
	}
	@Test
	public void testcalOutgoing1() throws FileNotFoundException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		defaultIndex();
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();
		HashSet<String> webpages=new HashSet<String>();
		HashMap<String, Integer> url2index=new HashMap<String, Integer>();
		/*
		 * Create a mock webpages {"google.com","map.google.com"}
		 */
		webpages.add("google.com");
		webpages.add("map.google.com");
		
		/*
		 * Create a mock url2index "google.com"->0, "map.google.com"->1
		 */

		url2index.put("google.com", 0);
		url2index.put("map.google.com", 1);
		
		/*
		 * test part
		 */ 		
		PageRank mock=new PageRank(path1,path2);
		Method method = PageRank.class.getDeclaredMethod("calOutgoing", Set.class,HashMap.class);
		method.setAccessible(true);
		Object result = method.invoke(mock, webpages,url2index);
		double res=((double[][])result)[0][0];
		assertEquals(0.0,res,0);
			
	}
	@Test
	public void testdealWithDangling1() throws FileNotFoundException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		defaultIndex();
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();
		HashSet<String> webpages=new HashSet<String>();
		HashMap<String, Integer> url2index=new HashMap<String, Integer>();
		double[][] link=new double[2][2];
		/*
		 * Create a mock webpages {"google.com","map.google.com"}
		 */
		webpages.add("google.com");
		webpages.add("map.google.com");
		
		/*
		 * Create a mock url2index "google.com"->0, "map.google.com"->1
		 */

		url2index.put("google.com", 0);
		url2index.put("map.google.com", 1);
		
		/*
		 * test part
		 */ 		
		PageRank mock=new PageRank(path1,path2);
		Method method = PageRank.class.getDeclaredMethod("dealWithDangling", Set.class,HashMap.class,double[][].class);
		method.setAccessible(true);
		method.invoke(mock, webpages,url2index,link);
		assertEquals(link[0][1],0.0,0);
			
	}
	@Test
	public void testGeneratePageRank() throws FileNotFoundException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		defaultIndex();
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();
		
		/*
		 * test part
		 */ 		
		PageRank mock=new PageRank(path1,path2);
		Method method = PageRank.class.getDeclaredMethod("generatePageRank", int.class,double.class,double.class);
		method.setAccessible(true);
		method.invoke(mock, 20,1.0e-6,0.85);
			
	}

}
