package preprocess;
import java.io.IOException;
public class Preprocess {
	public static void generatePreprocess() throws IOException {
		/* body element keywords extractor module*/
		StringMapModule bodyExtractor=new StringMapModule(new ExtractBodyModule());
		ForwardIndexModuleInterface forwardBodyMod=new ForwardIndexModule(bodyExtractor,"src/res/ForwardIndexDataset");
		InvertedIndexModuleInterface invertedBodyMod=new InvertedIndexModule(forwardBodyMod,"src/res/InvertedIndexDataset");
		bodyExtractor.generateKeywordStrMap("src/res/html/");
		
		/* url extractor module*/
		StringMapModule urlExtractor=new StringMapModule(new ExtractLinkModule());
		ForwardIndexModuleInterface forwardLinkMod=new ForwardIndexModule(urlExtractor,"src/res/linkForwardIndexDataset");
		InvertedIndexModuleInterface invertedLinkMod=new InvertedIndexModule(forwardLinkMod,"src/res/linkInvertedIndexDataset");
		urlExtractor.generateKeywordStrMap("src/res/html/");
		
		/* title extractor module*/
		StringMapModule titleExtractor=new StringMapModule(new ExtractTitleModule());
		ForwardIndexModuleInterface forwardTitleMod=new ForwardIndexModule(titleExtractor,"src/res/titleForwardIndexDataset");
		try {
			titleExtractor.generateKeywordStrMap("src/res/html/");
		}
		catch(Exception e) {
		}
	}
}
