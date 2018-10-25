/**
 * implements the search interface, this program gets a list of keywords and 
 * returns the related URLs with ranking
 * 
 * @author: ZHAO Zinan
 * @since: 30-Sep-2018
 */

package query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CommonQuery implements QueryInterface {
	private HashMap<String, ArrayList<String>> forwardIndex;
	private HashMap<String, ArrayList<String>> invertedIndex;

	/**
	 * use the default path (src/res/) as the dataset path to init the instance
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public CommonQuery() throws ClassNotFoundException, IOException {
		this("src/res/InvertedIndexDataset", "src/res/ForwardIndexDataset");
	}

	/**
	 * read forwardIndex and invertedIndex from dataset under resPath directory
	 * 
	 * @param resPath
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public CommonQuery(String invertPath, String forwardPath) throws ClassNotFoundException, IOException {
		try {
			// read inverted index
			FileInputStream in = new FileInputStream(new File(invertPath));
			ObjectInputStream input = new ObjectInputStream(in);
			this.invertedIndex = (HashMap<String, ArrayList<String>>) input.readObject();
			input.close();
			in.close();

			// read forward index
			in = new FileInputStream(new File(forwardPath));
			input = new ObjectInputStream(in);
			this.forwardIndex = (HashMap<String, ArrayList<String>>) input.readObject();
			input.close();
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * search for a list of keywords and rank the results
	 * 
	 * @param keywords
	 * @param rankMethod: tfidf or pagerank ranking method
	 * @return a list ranks urls
	 */
	public ArrayList<String> search(ArrayList<String> keywords, String rankMethod) 
			throws URLNotFoundException, URLNotinComparatorException, RankMethodNotFoundException{
				// keyword to lower case
				for (int i = 0; i < keywords.size(); i++) {
					keywords.set(i, keywords.get(i).toLowerCase());
				}

				ArrayList<String> urls = new ArrayList<>();
				// find urls for each keyword
				for (String keyword : keywords) {
					urls.addAll(this.querySingleKeyword(keyword));
				}

				// remove all the duplicates from the urls list
				urls = new ArrayList<String>(new HashSet<>(urls));

				// use rank interface to rank the urls
				ArrayList<String> rankedUrls;
				if (rankMethod.toLowerCase() == "tfidf") {
					RankInterface ranker = new TFIDFRank(this.invertedIndex, this.forwardIndex);
					rankedUrls = ranker.rank(urls, keywords);
				} else if (rankMethod.toLowerCase() == "pagerank") {
					RankInterface ranker = new PageRank(this.invertedIndex, this.forwardIndex);
					rankedUrls = ranker.rank(urls, keywords);
				} else {
					throw new RankMethodNotFoundException();
				}
				

				return rankedUrls;
	}
	
	public ArrayList<String> search(ArrayList<String> keywords) {
		return this.search(keywords, "pagerank");
	}

	public ArrayList<String> search(String keywords) {
		return this.search(new ArrayList<String>(Arrays.asList(keywords.split(" "))));
	}

	/**
	 * search for a single keyword among the urls
	 * 
	 * @param keyword
	 * @return all the urls with the keyword
	 */
	private ArrayList<String> querySingleKeyword(String keyword) {
		ArrayList<String> result = this.invertedIndex.get(keyword);
		
		if (result == null) {
			return new ArrayList<String>();
		}
		return result;
	}

}
