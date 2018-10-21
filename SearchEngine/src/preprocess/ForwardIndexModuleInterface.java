package preprocess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ForwardIndexModuleInterface {
	public abstract void generateForwardIndexMap(HashMap<String, String> origin_map) throws IOException;
	public abstract void subscribe(InvertedIndexModuleInterface observer);
	public abstract HashMap<String,ArrayList<String>> getForwardIndexMap();
}
