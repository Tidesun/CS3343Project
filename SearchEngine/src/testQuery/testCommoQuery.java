package testPreprocess;

import static org.junit.Assert.*;
import org.junit.Test;

import query.CommonQuery;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class testCommoQuery {

	@Test
	public void testCommonQuery1() {
		//this("src/res/InvertedIndexDataset", "src/res/ForwardIndexDataset");
		CommonQuery query = new CommonQuery("src/res/InvertedIndexDataset","src/res/ForwardIndexDataset");
		
		ArrayList<String> geturl = query.search("airbnb");
		ArrayList<String> result = new ArrayList<String>();
		result.add("src\\res\\html\\www.airbnb.com.html");
		assertEquals(result, geturl);
	}
	
	@Test
	public void testCommonQuery2() {
		//this("src/res/InvertedIndexDataset", "src/res/ForwardIndexDataset");
		CommonQuery query = new CommonQuery("src/res/InvertedIndexDataset","src/res/ForwardIndexDataset");
		
		ArrayList<String> geturl = query.search("google");
		ArrayList<String> result = new ArrayList<String>();
		result.add("src\\res\\html\\www.stackoverflow.com.html");
		result.add("src\\res\\html\\www.w3schools.com_html_default.asp.html");
		result.add("src\\res\\html\\www.canva.com.html");
		assertEquals(result, geturl);
	}
}
