
package testPreprocess;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.Before;
import org.junit.Rule;

import preprocess.*;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestInvertedIndexModule {
	
	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file1;
	File file2;
	HashMap<String, ArrayList<String>> forward;
	InvertedIndexModuleInterface inverted;
	
	@Before
	public void setUp() {
		file1 = new File(temp.getRoot(), "InvertedIndexDataset");
		file2 = new File(temp.getRoot(), "ForwardIndexDataset");
		String path1 = (String) file1.getPath();
		String path2 = (String) file2.getPath();
		forward = new HashMap<>();
		StringMapModule urlExtractor=new StringMapModule(new ExtractLinkModule());
		ForwardIndexModuleInterface forwardLinkMod=new ForwardIndexModule(urlExtractor,path1);
		inverted=new InvertedIndexModule(forwardLinkMod,path2);
	}
	
	// new empty hashmap
	@Test
	public void testGenerateInvertedIndexMap1() throws IOException {
		inverted.generateInvertedIndexMap(forward);
		assertEquals(new HashMap<String, ArrayList<String>>(), inverted.getInvertedIndexMap());
	}
	
	// normal hashmap with content
	@Test
	public void testGenerateInvertedIndexMap2() throws IOException {
		// parameter
		forward.put("github.html", new ArrayList<>(Arrays.asList("github")));
		
		inverted.generateInvertedIndexMap(forward);

		// result 
		HashMap<String, ArrayList<String>> res = new HashMap<>();
		res.put("github", new ArrayList<>(Arrays.asList("github.html")));
		
		assertEquals(res, inverted.getInvertedIndexMap());
	}
	
	// normal hashmap with content
	@Test
	public void testGenerateInvertedIndexMap3() throws IOException {
		// parameter
		forward.put("github.html", new ArrayList<>(Arrays.asList("github")));
		forward.put("github pages", new ArrayList<>(Arrays.asList("github")));
		
		inverted.generateInvertedIndexMap(forward);

		// result
		HashMap<String, ArrayList<String>> res = new HashMap<>();
		
		ArrayList<String> urlsList = new ArrayList<String>();
		urlsList.add("github.html");
		urlsList.add("github pages");
		res.put("github", urlsList);
		
		assertEquals(res, inverted.getInvertedIndexMap());
	}
	 
	
	
	// normal hashmap with content
	@Test
	public void testGenerateInvertedIndexMap4() throws IOException {
		// parameter
		forward.put("github.html", new ArrayList<>(Arrays.asList("github")));
		forward.put("github.html", new ArrayList<>(Arrays.asList("github")));
		
		inverted.generateInvertedIndexMap(forward);

		// result
		HashMap<String, ArrayList<String>> res = new HashMap<>();
		
		ArrayList<String> urlsList = new ArrayList<String>();
		urlsList.add("github.html");;
		res.put("github", urlsList);
		
		assertEquals(res, inverted.getInvertedIndexMap());
	}
	 
}
