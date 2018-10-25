package query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import util.*;

public class PageRank extends AbstractRank {
	private HashMap<String, Double> weights = new HashMap<>();
	
	public PageRank(HashMap<String, ArrayList<String>> inverted, HashMap<String, ArrayList<String>> forward) {
		super(inverted, forward);
	}
	
	public double weigh(String url, ArrayList<String> keywords) 
			throws MatrixMultiplyException, MatrixSubException, URLNotFoundException {
		if (weights.keySet().size() <= 0) {
			this.generatePageRank();
		}
		if (weights.get(url) == null) {
			throw new URLNotFoundException();
		}
		return weights.get(url);
	}
	
	public void updateWeights(HashMap<String, Double> weights) {
		this.weights = weights;
	}
	
	private void generatePageRank(int maxIter, double tol,double dampingFactor) throws MatrixMultiplyException, MatrixSubException {
		// init the pagerank of each url
		int numURL = invertedIndex.keySet().size();
		double[][] pageRankArr = new double[numURL][1];
		for (double[] pageRankArrEle: pageRankArr) {
			Arrays.fill(pageRankArrEle, (double)1/numURL);
		}
		
		Matrix pageRank = new Matrix(pageRankArr);
		
		// mapping between url and index in pageRnak matrix
		HashMap<String, Integer> url2index = new HashMap<>();
		HashMap<Integer, String> index2url = new HashMap<>();
		int i=0;
		for (String key: invertedIndex.keySet()) {
			index2url.put(i, key);
			url2index.put(key, i++);
		}
		
		// the outgoing link of each url pair
		double[][] link = new double[numURL][numURL];
		for (String key: forwardIndex.keySet()) {
			int index = url2index.get(key);
			ArrayList<String> outUrls = forwardIndex.get(key);
			int outNum = outUrls.size();
			
			for (String url: outUrls) {
				int outIndex = url2index.get(url);
				link[outIndex][index] = (double)1/outNum;
			}
		}
		
		// find dangling nodes (no outgoing link)
		Iterator<HashMap.Entry<String, ArrayList<String>>> it = forwardIndex.entrySet().iterator();
		while(it.hasNext()) {
			HashMap.Entry<String, ArrayList<String>> pair = it.next();
			if (pair.getValue().size() == 0) {
				String danglingurl = pair.getKey();
				int danglingindex = url2index.get(danglingurl);
				
				for (int j=0; j<numURL; j++) {
					link[j][danglingindex] = (double) 1/numURL;
				}
			}
			it.remove();
		}
		
		Matrix linkweight = new Matrix(link);

		// iterations up to maxIter times
		double error=0;
		for (int x=0; x<maxIter; x++) {
			Matrix previousPageRank = pageRank;
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
	
	private void generatePageRank() throws MatrixMultiplyException, MatrixSubException {
		// TODO: modify the parameters according to testing result
		generatePageRank(100, 1.0e-6, 0.85);
	}
}
