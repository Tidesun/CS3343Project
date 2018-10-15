package testQuery;

import static org.junit.Assert.*;
import query.CommonQuery;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class testCommonQuery {
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
	
	@Test
	public void testSearch_01() throws IOException {

		ArrayList<String> keywords = new ArrayList<String>();
		ArrayList<String> expectedURLs = new ArrayList<>();

		ArrayList<String> realURLs = commonQuery.search(keywords);
		assertEquals(expectedURLs, realURLs);
	}
	
	@Test
	public void testSearch_02() throws IOException {
		//1.input data
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("airbnb");
		keywords.add("ins");
		
		
		//2.expected result
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("www.airbnb.com");
		expectedURLs.add("www.instragram.com");
		
		//3.test execution
		ArrayList<String> realURLs = commonQuery.search(keywords);
		
		//4.test result
		assertEquals(expectedURLs, realURLs);
	}
	@Test
	public void testSearch_03() throws IOException {
	
		ArrayList<String> keywords = new ArrayList<String>();
		//keywords.add("airbnb");
		keywords.add("ins");
		keywords.add("apple");
		
		
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("www.airbnb.com");
		expectedURLs.add("www.instragram.com");
		expectedURLs.add("www.apple.com");
		
		
		ArrayList<String> realURLs =commonQuery.search(keywords);
		assertEquals(expectedURLs, realURLs);
	}
	@Test
	public void testquerySingleKeyword_01() {
		String singlekeyword = null;
		ArrayList<String> expectedURLs = new ArrayList<>();
		
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		assertEquals(expectedURLs, realURLs);
			
	}
	@Test
	public void testquerySingleKeyword_02() {
		String singlekeyword = "accomdation";
		
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("www.airbnb.com");
		expectedURLs.add("www.airbnb.com");
		
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		
		assertEquals(expectedURLs, realURLs);
			
	}
	@Test
	public void testquerySingleKeyword_03() {
		String singlekeyword = "sign";
		
		ArrayList<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("www.facebook.com");
		expectedURLs.add("www.twitter.com");
		
		ArrayList<String> realURLs = commonQuery.search(singlekeyword);
		
		assertEquals(expectedURLs, realURLs);
			
	}
}
