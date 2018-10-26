package query;

import java.io.IOException;
import java.util.ArrayList;

public interface QueryInterface {
	public ArrayList<String> search(ArrayList<String> keywords) throws ClassNotFoundException, IOException;
	public ArrayList<String> search(String keywords) throws ClassNotFoundException, IOException;
}
