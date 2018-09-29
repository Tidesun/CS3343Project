package preprocess;
import java.util.ArrayList;
import java.util.HashMap;

public interface ForwardIndexModuleInterface {
	public abstract void generateForwardIndexMap(HashMap<String, String> origin_map);
	public abstract void subscribe(InvertedIndexModuleInterface observer);
	public abstract HashMap<String,ArrayList<String>> getForwardIndexMap();
}
