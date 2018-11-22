/**
 * TFIDF implementation of rank interface
 * rank the web pages according to the frequency of each keyword and the keyword appears in less web pages are preferred.
 * 
 * @author  ZHAO Zinan
 * @since   30-Sep-2018
 */

package query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class TFIDFRank.
 */
public class TFIDFRank extends AbstractRank {
	
	/**
	 * Instantiates a new TFIDF rank.
	 *
	 * @param invertedPath the inverted path
	 * @param forwardPath the forward path
	 * @throws FileNotFoundException the file not found exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public TFIDFRank(String invertedPath, String forwardPath) throws FileNotFoundException, ClassNotFoundException, IOException {
		super(invertedPath, forwardPath);
	}
	
	/**
	 * Instantiates a new TFIDF rank.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public TFIDFRank() throws FileNotFoundException, ClassNotFoundException, IOException {
		super("src/res/dataset/InvertedIndexDataset", "src/res/dataset/ForwardIndexDataset");
	}
	
	/**
	 * cal the weight of one url given a list of keywords.
	 *
	 * @param url the url
	 * @param keywords   a list of keywords that will be used to measure the weight
	 * @return the double
	 * @throws URLNotFoundException the URL not found exception
	 */
	public double weigh (String url, ArrayList<String> keywords) throws URLNotFoundException {
		// make sure the keywords are in the url
		ArrayList<String> keywordsInURL = forwardIndex.get(url);
		if (keywordsInURL == null) {
			throw new URLNotFoundException(String.format("WARNING: URL %s is not in the forwarIndex hashmap", url));
		}
		for (int i=0;i<keywords.size();i++) {
			if (!keywordsInURL.contains(keywords.get(i))&&(keywords.contains(keywords.get(i)))) {
				keywords.remove(keywords.get(i));
			}
		}
		
		// IDF for each keyword
		HashMap<String, Double> idf = idfWeigh(keywords);
		
		// TF for each keyword
		HashMap<String, Double> tf = tfWeigh(url, keywords);
		
		// because the range of tf is equal or smaller to idf
		// we traverse tf's keyword
		double weight = 0;
		for (String keyword: tf.keySet()) {
			weight += tf.get(keyword) * idf.get(keyword);
		}
		
		return weight;
	}
	
	/**
	 * cal the idf weight of each keyword in the keywords list.
	 *
	 * @param keywords the keywords
	 * @return map the keywords to its idf weight in current dataset
	 */
	private HashMap<String, Double> idfWeigh (ArrayList<String> keywords) {
		// number of urls
		int totalNumURL = this.forwardIndex.keySet().size();
				
		HashMap<String, Double> idf = new HashMap<>();
		for (int i=0;i<keywords.size();i++) {

			try {
				int numOfUrl = invertedIndex.get(keywords.get(i)).size();   		// number of urls that keyword appears in
				idf.put(keywords.get(i), Math.log(numOfUrl / totalNumURL));
			} catch (NullPointerException e) {
				System.out.printf("ERROR: cannot find keyword %s in the invertedIndex hashmap", keywords.get(i));
				System.out.println(e);
			}
			
		}
		
		return idf;
	}
	
	/**
	 *  
	 * cal the tf weight of each keyword in url .
	 *
	 * @param url the url
	 * @param keywords the keywords
	 * @return map the keyword to its tf weight in the url
	 * @throws URLNotFoundException the URL not found exception
	 */
	private HashMap<String, Double> tfWeigh (String url, ArrayList<String> keywords) throws URLNotFoundException {
		ArrayList<String> keywordsInURL = forwardIndex.get(url);
		if (keywordsInURL == null) {
			throw new URLNotFoundException(String.format("ERROR: url %s is not in forwardIndex hashmap", url));
		}
		
		// count number of occurrence of each keyword in the url
		HashMap<String, Integer> numOccurrence = new HashMap<>();
		for (int i=0;i<keywords.size();i++) {
			if (! numOccurrence.keySet().contains(keywords.get(i))) {
				int times = Collections.frequency(keywordsInURL, keywords.get(i));
				numOccurrence.put(keywords.get(i), times);
			}
		}
		
		// total number of words in the url
		int totalWords = keywordsInURL.size();
		
		// cal tf weight 
		HashMap<String, Double> tfWeight = new HashMap<>();
		for (String keyword: numOccurrence.keySet()) {
			tfWeight.put(keyword,  (double) numOccurrence.get(keyword)/totalWords);
		}
		
		return tfWeight;
	}
}
