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
	private RankInterface ranker;
	private String iPath;
	private String fPath;

	/**
	 * use the default path (src/res/) as the dataset path to init the instance
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public CommonQuery() throws ClassNotFoundException, IOException {
		this("src/res/dataset/InvertedIndexDataset", "src/res/dataset/ForwardIndexDataset");
	}

	/**
	 * read forwardIndex and invertedIndex from dataset under resPath directory
	 * 
	 * @param invertPath
     * @param forwardPath
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public CommonQuery(String invertPath, String forwardPath) throws ClassNotFoundException, IOException {
		try {
			this.ranker=null;
			
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
		
		this.iPath = invertPath;
		this.fPath = forwardPath;
	}

	/**
	 * search for a list of keywords and rank the results
	 * 
	 * @param keywords
	 * @param rankMethod: tfidf or pagerank ranking method
	 * @return a list ranks urls
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public ArrayList<String> search(ArrayList<String> keywords, String rankMethod, String invertedPath, String forwardPath) 
			throws URLNotFoundException, URLNotinComparatorException, RankMethodNotFoundException, ClassNotFoundException, IOException{
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
					this.ranker = new TFIDFRank(invertedPath, forwardPath);
					rankedUrls = ranker.rank(urls, keywords);
				} else if (rankMethod.toLowerCase() == "pagerank") {
					this.ranker = new PageRank(invertedPath, forwardPath);
					rankedUrls = ranker.rank(urls, keywords);
				} else {
					throw new RankMethodNotFoundException();
				}
				

				return rankedUrls;
	}
	
	public ArrayList<String> search(ArrayList<String> keywords, String rankMethod) 
			throws ClassNotFoundException, IOException {
		if (rankMethod == "tfidf") {
			return this.search(keywords, rankMethod, this.iPath, this.fPath);
		} else if (rankMethod == "pagerank") {
			return this.search(keywords, rankMethod, "src/res/dataset/linkForwardIndexDataset", "src/res/dataset/linkInvertedIndexDataset");
		}
		
		throw new RankMethodNotFoundException();
	}

	public ArrayList<String> search(String keywords, String rankMethod)
			throws ClassNotFoundException, IOException {
	    ArrayList<String> splitkeywords = new ArrayList<String>(Arrays.asList(keywords.split(" ")));
		if (rankMethod == "tfidf") {
			return this.search(splitkeywords, rankMethod, this.iPath, this.fPath);
		} else if (rankMethod == "pagerank") {
			return this.search(splitkeywords, rankMethod, "src/res/dataset/linkForwardIndexDataset", "src/res/dataset" +
                    "/linkInvertedIndexDataset");
		}

		throw new RankMethodNotFoundException();
	}
	
	public ArrayList<String> search(ArrayList<String> keywords)
			throws ClassNotFoundException, IOException {
		return this.search(keywords, "pagerank");
	}

	public ArrayList<String> search(String keywords) 
			throws ClassNotFoundException, IOException {
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
