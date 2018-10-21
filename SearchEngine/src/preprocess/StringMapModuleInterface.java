package preprocess;

import java.io.IOException;

public interface StringMapModuleInterface {
	public abstract void generateKeywordStrMap(String path) throws IOException;
	public abstract void subscribe(ForwardIndexModuleInterface observer);

}
