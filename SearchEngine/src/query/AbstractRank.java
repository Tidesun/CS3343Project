package query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public abstract class AbstractRank implements RankInterface{
	protected HashMap<String, ArrayList<String>> invertedIndex;
	protected HashMap<String, ArrayList<String>> forwardIndex;
	
	/**
	 * constructor for AbstractRank
	 * @param invertedIndex 
	 * @param forwardIndex
	 */
	AbstractRank(HashMap<String, ArrayList<String>> invertedIndex, HashMap<String, ArrayList<String>> forwardIndex) {
		this.invertedIndex = invertedIndex;
		this.forwardIndex = forwardIndex;
	}
	
	/**
	 * rank the given urls list with weight method
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
	
	public abstract double weigh(String url, ArrayList<String> keywords);
}
