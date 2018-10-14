/**
 * TFIDF implementation of rank interface
 * rank the web pages according to the frequency of each keyword and the keyword appears in less web pages are preferred.
 * 
 * @author  ZHAO Zinan
 * @since   30-Sep-2018
 */

package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

public class TFIDFRank implements RankInterface {
	private HashMap<String, ArrayList<String>> invertedIndex;
	private HashMap<String, ArrayList<String>> forwardIndex;
	
	/**
	 * constructor for tfidf
	 * @param invertedIndex 
	 * @param forwardIndex
	 */
	public TFIDFRank (HashMap<String, ArrayList<String>> invertedIndex, HashMap<String, ArrayList<String>> forwardIndex) {
		this.invertedIndex = invertedIndex;
		this.forwardIndex = forwardIndex;
	}
	
	/**
	 * rank the given urls list with TFIDF method
	 * @param  urls
	 * @param  keywords
	 * @throws URLNotinComparatorException
	 */
	public ArrayList<String> rank (ArrayList<String> urls, ArrayList<String> keywords) 
			throws URLNotinComparatorException, URLNotFoundException {
		
		// define SortByWeight class for sort the urls later
		class SortByWeight implements Comparator<String> {
			HashMap<String, Double> weightMap;
			
			SortByWeight(HashMap<String, Double> weight) {
				this.weightMap = weight;
			}
			
			public int compare(String a, String b) {
				// hashmap cannot get the URL
				if (weightMap.get(a) == null) {
					throw new URLNotinComparatorException(String.format("ERROR: cannot find weight of url %s", a));
				}
				if (weightMap.get(b) == null) {
					throw new URLNotinComparatorException(String.format("ERROR: cannot find weight of url %s", b));
				}
				
				if (weightMap.get(a) > weightMap.get(b)) {
					return 1;
				} else if (weightMap.get(a) < weightMap.get(b)) {;
					return -1;
				} 
				
				return 0;
			}
		}
		
		HashMap<String, Double> weightMap = new HashMap<>();
		for (String url: urls) {
			weightMap.put(url,  weigh(url, keywords));
		}
		
		Collections.sort(urls, new SortByWeight(weightMap));
		
		return urls;
	}
	
	/**
	 * cal the weight of one url given a list of keywords
	 * @param url        
	 * @param keywords   a list of keywords that will be used to measure the weight
	 */
	public double weigh (String url, ArrayList<String> keywords) throws URLNotFoundException {
		// make sure the keywords are in the url
		ArrayList<String> keywordsInURL = forwardIndex.get(url);
		if (keywordsInURL == null) {
			throw new URLNotFoundException(String.format("WARNING: URL %s is not in the forwarIndex hashmap", url));
		}
		
		for (String keyword: keywords) {
			if (!keywordsInURL.contains(keyword)) {
				keywords.remove(keyword);
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
	 * cal the idf weight of each keyword in the keywords list
	 * @param  keywords
	 * @return map the keywords to its idf weight in current dataset
	 */
	private HashMap<String, Double> idfWeigh (ArrayList<String> keywords) {
		// number of urls
		int totalNumURL = this.forwardIndex.keySet().size();
				
		HashMap<String, Double> idf = new HashMap<>();
		for (String keyword: keywords) {

			try {
				int numOfUrl = invertedIndex.get(keyword).size();   		// number of urls that keyword appears in
				idf.put(keyword, Math.log(numOfUrl / totalNumURL));
			} catch (NullPointerException e) {
				System.out.printf("ERROR: cannot find keyword %s in the invertedIndex hashmap", keyword);
				System.out.println(e);
			}
			
		}
		
		return idf;
	}
	
	/** 
	 * cal the tf weight of each keyword in url 
	 * @param  url
	 * @param  keywords
	 * @return map the keyword to its tf weight in the url  
	 */
	private HashMap<String, Double> tfWeigh (String url, ArrayList<String> keywords) throws URLNotFoundException {
		ArrayList<String> keywordsInURL = forwardIndex.get(url);
		if (keywordsInURL == null) {
			throw new URLNotFoundException(String.format("ERROR: url %s is not in forwardIndex hashmap", url));
		}
		
		// count number of occurrence of each keyword in the url
		HashMap<String, Integer> numOccurrence = new HashMap<>();
		for (String keyword: keywords) {
			if (! numOccurrence.keySet().contains(keyword)) {
				int times = Collections.frequency(keywordsInURL, keyword);
				numOccurrence.put(keyword, times);
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
