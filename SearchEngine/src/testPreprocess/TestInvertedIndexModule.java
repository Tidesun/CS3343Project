
package testPreprocess;

import static org.junit.Assert.*;
import org.junit.Test;

import preprocess.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestInvertedIndexModule {
	// new empty hashmap
	@Test
	public void testGenerateInvertedIndexMap1() throws IOException {
		HashMap<String, ArrayList<String>> forward = new HashMap<>();
		InvertedIndexModule inverted = new InvertedIndexModule(new ForwardIndexModule(new StringMapModule()));
		inverted.generateInvertedIndexMap(forward);
		assertEquals(new HashMap<String, ArrayList<String>>(), inverted.getInvertedIndexMap());
	}
	
	// normal hashmap with content
	@Test
	public void testGenerateInvertedIndexMap2() throws IOException {
		// parameter
		HashMap<String, ArrayList<String>> forward = new HashMap<>();
		forward.put("github.html", new ArrayList<>(Arrays.asList("github")));
		forward.put("github pages", new ArrayList<>(Arrays.asList("github")));
		forward.put("airbnb.html", new ArrayList<>(Arrays.asList("airbnb")));
		
		InvertedIndexModule inverted = new InvertedIndexModule(new ForwardIndexModule(new StringMapModule()));
		inverted.generateInvertedIndexMap(forward);

		// result
		HashMap<String, ArrayList<String>> res = new HashMap<>();
		res.put("github", new ArrayList<>(Arrays.asList("github.html", "github pages")));
		res.put("airbnb", new ArrayList<>(Arrays.asList("airbnb.html")));
		
		assertEquals(res, inverted.getInvertedIndexMap());
	}
	
	// normal hashmap with content
	@Test
	public void testGenerateInvertedIndexMap3() throws IOException {
		// parameter
		HashMap<String, ArrayList<String>> forward = new HashMap<>();
		forward.put("github.html", new ArrayList<>(Arrays.asList("github", "git")));
		forward.put("airbnb.html", new ArrayList<>(Arrays.asList("airbnb", "air")));
		
		InvertedIndexModule inverted = new InvertedIndexModule(new ForwardIndexModule(new StringMapModule()));
		inverted.generateInvertedIndexMap(forward);

		// result
		HashMap<String, ArrayList<String>> res = new HashMap<>();
		res.put("github", new ArrayList<>(Arrays.asList("github.html")));
		res.put("git", new ArrayList<>(Arrays.asList("github.html")));
		res.put("air", new ArrayList<>(Arrays.asList("airbnb.html")));
		res.put("airbnb", new ArrayList<>(Arrays.asList("airbnb.html")));
		
		assertEquals(res, inverted.getInvertedIndexMap());
	}
}
