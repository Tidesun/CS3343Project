package testPreprocess;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import preprocess.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class testExtractLinkModule {

	@Rule
	public TemporaryFolder temp= new TemporaryFolder();
	File file;
	ExtractLinkModule extractLinkModule;
   
    @Before
    public void setUp(){
        file = new File(temp.getRoot(), "mytestfile.html");
        extractLinkModule = new ExtractLinkModule();
    }
    /*
     * <li><a target='_blank' href=\"http://www.cityu.edu.hk/lib/\" >Library</a></li> 
     * want:www.cityu.edu.hk/lib/
     * compute:
     */ 
	@Test
	public void testUsingTempFolder1() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a target='_blank' href=\"http://www.cityu.edu.hk/lib/\" >Library</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "www.cityu.edu.hk/lib/ "; 

		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
	 * <li><a href="/cityu/qlink/aims.htm" >AIMS</a></li>
	 */
	@Test
	public void testUsingTempFolder2() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"/cityu/qlink/aims.htm\" >AIMS</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		assertEquals(res,result); 
	}
	
	/*
	 * <li><a href="http://libguides.library.cityu.edu.hk/cityuscholars" >CityU Scholars</a></li>
	 */
	@Test
	public void testUsingTempFolder3() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"http://libguides.library.cityu.edu.hk/cityuscholars\" >CityU Scholars</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "libguides.library.cityu.edu.hk/cityuscholars "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
     * <li><a href="https://www.cityu.edu.hk/portal/#PasswordManagement">Change Password</a></li> 
     * want:www.cityu.edu.hk/portal/ 
     * compute:
     */ 
	@Test
	public void testUsingTempFolder4() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a href=\"https://www.cityu.edu.hk/portal/#PasswordManagement\">Change Password</a></li>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "www.cityu.edu.hk/portal/ "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
		
	 /*
     * <div class='slide-button'><a href='https://newscentre.cityu.edu.hk/media/news/2018/10/16/
     * cityu-and-npm-explore-animal-world-using-new-media-their-3rd-collaboration'>READ MORE
     *  <i class='icon-angle-right'></i></a></div>
     * want:https://newscentre.cityu.edu.hk/media/news/2018/10/16/
     *      cityu-and-npm-explore-animal-world-using-new-media-their-3rd-collaboration
     * compute:
     */ 
	@Test
	public void testUsingTempFolder5() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<div class='slide-button'><a href='https://newscentre.cityu.edu.hk/media/news/2018/10/16/cityu-and-npm-explore-animal-world-using-new-media-their-3rd-collaboration'>READ MORE <i class='icon-angle-right'></i></a></div>\n" + 
				"";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "https://newscentre.cityu.edu.hk/media/news/2018/10/16/cityu-and-npm-explore-animal-world-using-new-media-their-3rd-collaboration "; 

		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
     * <a class="cityu-link-more" target='_blank' href="http://newscentre.cityu.edu.hk/">More News <i class="icon-double-angle-right" aria-hidden="true"></i></a>
     * want:http://newscentre.cityu.edu.hk/
     * compute:
     */ 
	@Test
	public void testUsingTempFolder6() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = " <a class=\"cityu-link-more\" target='_blank' href=\"http://newscentre.cityu.edu.hk/\">More News <i class=\"icon-double-angle-right\" aria-hidden=\"true\"></i></a>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "http://newscentre.cityu.edu.hk/ "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
     * <a href="https://www.youtube.com/watch?v=cxYLijHWosU" target='_blank'> 
     */ 
	@Test
	public void testUsingTempFolder7() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<a href=\"https://www.youtube.com/watch?v=cxYLijHWosU\" target='_blank'>\n" + 
				"";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	
	/*
     * <a href="http://www.cityu.edu.hk/2018-animal/index" target='_blank'> 
     */ 
	@Test
	public void testUsingTempFolder8() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<a href=\"http://www.cityu.edu.hk/2018-animal/index\" target='_blank'>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = "www.cityu.edu.hk/2018-animal/index "; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
	
		assertEquals(res,result); 
	}
	
	/*
     * <li><a href="http://www6.cityu.edu.hk/cdo/download/CampusDirectory.pdf">Printable Map</a> 
     */ 
	@Test
	public void testUsingTempFolder9() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<li><a target='_blank' href=\"http://www6.cityu.edu.hk/cdo/download/CampusDirectory.pdf\">Printable Map</a>";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
     * <!--
			<li><a href="http://www.cityu.edu.hk/vprt/talks" target="_blank">Lectures by renowned speakers</a></li>
		-->
     * want:
     * compute: www.cityu.edu.hk/vprt/talks
     */ 
	@Test
	public void testUsingTempFolder10() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<!--\n" + 
				"			<li><a href=\"http://www.cityu.edu.hk/vprt/talks\" target=\"_blank\">Lectures by renowned speakers</a></li>\n" + 
				"		-->";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	/*
     * <a href="mailto:webmaster@cityu.edu.hk">webmaster@cityu.edu.hk</a>  
     */ 
	@Test
	public void testUsingTempFolder11() throws IOException {
		File createdFile= temp.newFile("mytestfile.html");
		
		// construct temp(test) html file
		String content = "<a href=\"mailto:webmaster@cityu.edu.hk\">webmaster@cityu.edu.hk</a> ";
		
		PrintStream ps = new PrintStream(new FileOutputStream(createdFile));
		ps.println(content); 
		// what I think it should be
		String res = ""; 
		// what actually it is
		String result = extractLinkModule.WebPageExtraction(createdFile);
		
		assertEquals(res,result); 
	}
	
	
}
