package query;

import java.util.ArrayList;

public interface RankInterface {
	public ArrayList<String> rank(ArrayList<String> urls, ArrayList<String> keywords);
	public double weigh(String url, ArrayList<String> keywords);
}
