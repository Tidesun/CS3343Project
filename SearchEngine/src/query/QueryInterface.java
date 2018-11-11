package query;

import java.io.IOException;
import java.util.ArrayList;

public interface QueryInterface {
	ArrayList<String> search(String keywords, String rankmethod) throws ClassNotFoundException,
			IOException;
	ArrayList<String> search(String keywords) throws ClassNotFoundException, IOException;
}
