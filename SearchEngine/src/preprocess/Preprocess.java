package preprocess;
import java.io.IOException;
public class Preprocess {
	public static void generatePreprocess() throws IOException {
		StringMapModule stringMapMod=new StringMapModule();
		ForwardIndexModuleInterface forwardMod=new ForwardIndexModule(stringMapMod);
		InvertedIndexModuleInterface invertedMod=new InvertedIndexModule(forwardMod);
		stringMapMod.generateKeywordStrMap("src/res/html/");
	}
}
