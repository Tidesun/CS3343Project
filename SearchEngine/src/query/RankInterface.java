package query;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Interface of ranking algorithm.
 */
public interface RankInterface {
	
	/**
	 * Rank.
	 *
	 * @param urls the urls
	 * @param keywords the keywords
	 * @return the array list
	 */
	public ArrayList<String> rank(ArrayList<String> urls, ArrayList<String> keywords);
	
	/**
	 * Weigh.
	 *
	 * @param url the url
	 * @param keywords the keywords
	 * @return the double
	 */
	public double weigh(String url, ArrayList<String> keywords);
}
