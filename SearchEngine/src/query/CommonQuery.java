/**
 * implements the search interface, this program gets a list of keywords and 
 * returns the related URLs with ranking
 * 
 * @author: ZHAO Zinan
 * @since: 30-Sep-2018
 */

package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CommonQuery implements QueryInterface{
	private HashMap<String, ArrayList<String>> forwardIndex;
	private HashMap<String, ArrayList<String>> invertedIndex;
	
	/**
	 * use the default path (src/res/) as the dataset path to init the instance
	 */
	public CommonQuery() {
		this("src/res/InvertedIndexDataset", "/src/res/ForwardIndexDataset");
	}
	
	/**
	 * read forwardIndex and invertedIndex from dataset under resPath directory
	 * @param resPath
	 */
	public CommonQuery(String invertPath, String forwardPath) {
		try {
			// read inverted index
			FileInputStream in = new FileInputStream(new File(invertPath));
			ObjectInputStream input = new ObjectInputStream(in);
			this.invertedIndex = (HashMap<String, ArrayList<String>>)input.readObject();
			input.close();
			in.close();
			
			// read forward index
			in = new FileInputStream(new File(forwardPath));
			input = new ObjectInputStream(in);
			this.forwardIndex = (HashMap<String, ArrayList<String>>)input.readObject();
			input.close();
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * search for a list of keywords and rank the results
	 * @param  keywords
	 * @return a list ranks urls 
	 */
	public ArrayList<String> search (ArrayList<String> keywords) {
		ArrayList<String> urls = new ArrayList<>();
		// find urls for each keyword
		for (String keyword: keywords) {
			urls.addAll(this.querySingleKeyword(keyword));
		}
		
		// remove all the duplicates from the urls list
		urls = new ArrayList<String>(new HashSet<>(urls));
		
		// use rank interface to rank the urls
		RankInterface ranker = new TFIDFRank(this.invertedIndex, this.forwardIndex);
		ArrayList<String> rankedUrls = ranker.rank(urls, keywords);
		
		return rankedUrls; 
	}
	
	/**
	 * search for a single keyword among the urls
	 * @param  keyword
	 * @return all the urls with the keyword
	 */
	private ArrayList<String> querySingleKeyword (String keyword) {
		return this.invertedIndex.get(keyword.toLowerCase());
	}
	
}
