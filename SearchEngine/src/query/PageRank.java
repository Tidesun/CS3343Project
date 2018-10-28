/**
 * use pagerank to rank the urls in searching result
 */
package query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import util.*;

public class PageRank extends AbstractRank {
	// weight for each url in union(invertedIndex, forwardIndex)
	// url : weight of the url
	private HashMap<String, Double> weights = new HashMap<>();
	
	/**
	 * PageRank constructor with custom path to inverted and forward index
	 * @param invertedPath
	 * @param forwardPath
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PageRank(String invertedPath, String forwardPath) throws FileNotFoundException, ClassNotFoundException, IOException {
		super(invertedPath, forwardPath);
	}
	
	/**
	 * PageRank constructor with default path to inverted and forward index
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PageRank() throws FileNotFoundException, ClassNotFoundException, IOException {
		super("src/res/linkInvertedIndexDataset", "src/res/linkForwardIndexDataset");
	}
	
	/**
	 * get the weight of a url from the generated weight HashMap
	 */
	public double weigh(String url, ArrayList<String> keywords) 
			throws MatrixMultiplyException, MatrixSubException {
		if (weights.keySet().size() <= 0) {
			this.generatePageRank();
		}
		if (weights.get(url) == null) {
			return 0;
		}
		return weights.get(url);
	}
	
	/**
	 * provide a way to update the weight of a url
	 * @param weights
	 */
	public void updateWeights(HashMap<String, Double> weights) {
		this.weights = weights;
	}
	
	/**
     * cal outgoing factor of each web page (directed)
	 * e.g.
	 * 	[[0, 0, 1/2],
	 *   [1, 0, 1/2],
	 *   [0, 1, 0]]
	 *   
	 * which means node0 -> node1
	 *             node1 -> node2
	 *             node2 -> node0, node1
	 *             
	 * we don't take loop (connect a vertex in itself) into consideration in this implementation 
	 * @param webpages
	 * @param url2index
	 */
	private double[][] calOutgoing(Set<String> webpages, HashMap<String, Integer> url2index){
		int numURL = webpages.size();
		// the outgoing link of each url pair
		double[][] link = new double[numURL][numURL];
		for (double[] row: link)
			Arrays.fill(row, 0.0);
			
		for (String key: forwardIndex.keySet()) {
			int index = url2index.get(key);
			ArrayList<String> outUrls = forwardIndex.get(key);
			int outNum = outUrls.size();
			
			for (String url: outUrls) {
				int outIndex = url2index.get(url);
				link[outIndex][index] = (double)1/outNum;
			}
		}
		
		return link;
	}
	
	/**
	 * deal with dangling nodes (nodes with no outgoing link)
	 * the outgoing factor of dangling nodes will be set as 1/(numURL-1) to evenly spread its importance
	 * @param webpages
	 * @param url2index
	 * @param link
	 */
	private void dealWithDangling(Set<String> webpages, HashMap<String, Integer> url2index, double[][] link) {
		int numURL = webpages.size();
		if (numURL == 1) {
			return;
		}
		
		Iterator<HashMap.Entry<String, ArrayList<String>>> it = forwardIndex.entrySet().iterator();
		while(it.hasNext()) {
			HashMap.Entry<String, ArrayList<String>> pair = it.next();
			if (pair.getValue().size() == 0) {
				String danglingurl = pair.getKey();
				int danglingindex = url2index.get(danglingurl);
				
				for (int j=0; j<numURL; j++) {
					link[j][danglingindex] = (double) 1/(numURL-1);
				}
			}
			it.remove();
		}
	}
	
	
	/**
	 * generate the pagerank hashmap and store it for later use
	 * 
	 * @param maxIter           : the max times of iteration
	 * @param tol               : the target diff between two consecutive iteration
	 * @param dampingFactor     : ensure the basic weight in each iteration 
	 * @throws MatrixMultiplyException : cannot do multiplication because of the shape of matrices
	 * @throws MatrixSubException      : the shape of matrices are diff in subtraction
	 */
	private void generatePageRank(int maxIter, double tol,double dampingFactor) 
			throws MatrixMultiplyException, MatrixSubException {
		// find all the urls in inverted index and forward index
		Set<String> webpageSet=new HashSet<String>(forwardIndex.keySet());
		webpageSet.addAll(invertedIndex.keySet());
		
		/* init the pagerank weight of each url
		 * [[<pageRank of url1>], 
		 *  [<pageRank of url2>], 
		 *  [<pageRank of url3>], 
		 *  [<pageRank of url4>]]
		 *  
		 *  use tow dimensional arr because of the interface of matrix 
		 */
		
		int numURL = webpageSet.size();
		double[][] pageRankArr = new double[numURL][1];
		for (double[] pageRankArrEle: pageRankArr) {
			Arrays.fill(pageRankArrEle, (double)1/numURL);
		}
		
		Matrix pageRank = new Matrix(pageRankArr);
		
		// mapping between url and index in pageRnak weight vector
		HashMap<String, Integer> url2index = new HashMap<>();
		HashMap<Integer, String> index2url = new HashMap<>();
		
		int i=0;
		for (String key: webpageSet) {
			index2url.put(i, key);
			url2index.put(key, i++);
		}
		
		/*
		 * cal outgoing factor of each web page (directed)
		 * explanation in detail can be found in doc of calOutgoing 
		 */
		double[][] link = calOutgoing(webpageSet, url2index);
		dealWithDangling(webpageSet, url2index, link);
		
		
		Matrix linkweight = new Matrix(link);
		
		// iterations up to maxIter times
		double error=0;
		for (int x=0; x<maxIter; x++) {
			Matrix previousPageRank = pageRank;
			/* 
			 * in each iteration, we do
			 *          [[0, 0, 1/2],         [[<pageRank of url0>],                        1-<dampingFacotr>
			 *         ( [1, 0, 1/2],   *      [<pageRank of url1>], )*<dampingFactor> +    -------------------
			 *           [0, 1, 0]]            [<pageRank of url2>]]                              numURL
			 *   
			 */
			pageRank = linkweight.mul(pageRank).mul(dampingFactor).add((1-dampingFactor)/numURL);
			
			// check the error of this iteration
			Matrix sub = pageRank.sub(previousPageRank);
			for (double[] errors: sub.getEles()) {
				error += errors[0];
			}
			if (error < tol) {
				break;
			}
		}
		
		if (error > tol) {
			System.out.printf("Warning: failed to coverage %f in %d interations", error, maxIter);
		} 
		
		// pageRank result to weight attribute
		this.weights = new HashMap<>();
		for (i=0; i<numURL; i++) {
			double weight = pageRank.getEles()[i][0];
			String urlKey = index2url.get(i);
			this.weights.put(urlKey, weight);
		}
	}
	
	/**
	 * generate PageRank with default parameters (max iterations, max error, damping factor)
	 * @throws MatrixMultiplyException
	 * @throws MatrixSubException
	 */
	private void generatePageRank() throws MatrixMultiplyException, MatrixSubException {
		// TODO: modify the parameters according to testing result
		generatePageRank(10, 1.0e-6, 0.85);
	}
}
