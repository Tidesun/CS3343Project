package query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * Abstract class for general ranking algorithm.
 */
public abstract class AbstractRank implements RankInterface {
	
	/** The inverted index. */
	protected HashMap<String, ArrayList<String>> invertedIndex;
	
	/** The forward index. */
	protected HashMap<String, ArrayList<String>> forwardIndex;
	
	/**
	 * Instantiates a new abstract rank.
	 *
	 * @param invertedPath the inverted path
	 * @param forwardPath the forward path
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	AbstractRank(String invertedPath, String forwardPath) 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			// read inverted index
			FileInputStream in = new FileInputStream(new File(invertedPath));
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

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	/*
	* Title: rank
	* Description: rank the given urls list with weight method
	* @param urls
	* @param keywords
	* @return
	* @throws URLNotinComparatorException
	* @throws URLNotFoundException 
	* @see query.RankInterface#rank(java.util.ArrayList, java.util.ArrayList) 
	*/
	
	public ArrayList<String> rank (ArrayList<String> urls, ArrayList<String> keywords) 
			throws URLNotinComparatorException, URLNotFoundException {
		
		// define SortByWeight class for sort the urls later
		class SortByWeight implements Comparator<String> {
			HashMap<String, Double> weightMap;
			
			SortByWeight(HashMap<String, Double> weight) {
				this.weightMap = weight;
			}
			
			@Override
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
	
	/*
	* Title: weigh
	* Description: 
	* @param url
	* @param keywords
	* @return 
	* @see query.RankInterface#weigh(java.lang.String, java.util.ArrayList) 
	*/
	
	public abstract double weigh(String url, ArrayList<String> keywords);
}
