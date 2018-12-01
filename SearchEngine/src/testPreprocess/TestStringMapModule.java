package testPreprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import preprocess.*;
import org.junit.rules.TemporaryFolder;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStringMapModule.
 */
public class TestStringMapModule {
	
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	
	/** The stringmap. */
	StringMapModule stringmap;
	
	/** The file. */
	File file;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		class StubExtract implements ExtractModuleInterface {
			// implements as return the first line
			public String WebPageExtraction(File filename) throws FileNotFoundException {
				Scanner sc =new Scanner(filename);
				String content="";
				if(sc.hasNext()) {
					content +=sc.nextLine()+"\n";
				}
				sc.close();
				return content;
			}
		}
		
		stringmap = new StringMapModule(new StubExtract());
	}
	
	/**
	 * Test generate keyword str map exception.
	 */
	// observer is null, throw null pointer exception
	@Test(expected=NullPointerException.class)
	public void testGenerateKeywordStrMapException() {
		try {
			stringmap.generateKeywordStrMap("res/html/");
		} catch (IOException e) {
			System.out.println("WARNING: IOException when testing StringMapModule.generateKeyworkMap");
			e.printStackTrace();
		}
	}
	
	/** 
	 * Test file with one file
	 */
	@Test
	public void testGenerateKeyword_onefile() {
		class StubForward implements ForwardIndexModuleInterface {
			public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException {}
			
			public void subscribe(InvertedIndexModuleInterface observer) {}
			
			public HashMap<String,ArrayList<String>> getForwardIndexMap() {
				return new HashMap<String, ArrayList<String>>();
			}
		}
		
		StubForward stubforward = new StubForward();
		stringmap.subscribe(stubforward);
		try {
			File tempFile1 = temp.newFile("file0");
			stringmap.generateKeywordStrMap(temp.getRoot().getAbsolutePath());
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
	 * Test file with more than one files
	 */
	@Test
	public void testGenerateKeyword_twofiles() {
		class StubForward implements ForwardIndexModuleInterface {
			public void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException {}
			
			public void subscribe(InvertedIndexModuleInterface observer) {}
			
			public HashMap<String,ArrayList<String>> getForwardIndexMap() {
				return new HashMap<String, ArrayList<String>>();
			}
		}
		StubForward stubforward = new StubForward();
		stringmap.subscribe(stubforward);
		try {
			File tempFile1 = temp.newFile("file1");
			File tempFile2 = temp.newFile("file2");
			stringmap.generateKeywordStrMap(temp.getRoot().getAbsolutePath());
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
