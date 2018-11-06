package preprocess;
import java.io.IOException;
public class Preprocess {
	public static void generatePreprocess() throws IOException {
		/* body element keywords extractor module*/
		StringMapModule bodyExtractor=new StringMapModule(new ExtractBodyModule());
		ForwardIndexModuleInterface forwardBodyMod=new ForwardIndexModule(bodyExtractor,"src/res/dataset/ForwardIndexDataset");
		InvertedIndexModuleInterface invertedBodyMod=new InvertedIndexModule(forwardBodyMod,"src/res/dataset/InvertedIndexDataset");
		bodyExtractor.generateKeywordStrMap("src/res/html/");
		
		/* url extractor module*/
		StringMapModule urlExtractor=new StringMapModule(new ExtractLinkModule());
		ForwardIndexModuleInterface forwardLinkMod=new ForwardIndexModule(urlExtractor,"src/res/dataset/linkForwardIndexDataset");
		InvertedIndexModuleInterface invertedLinkMod=new InvertedIndexModule(forwardLinkMod,"src/res/dataset/linkInvertedIndexDataset");
		urlExtractor.generateKeywordStrMap("src/res/html/");
		
		/* title extractor module*/
		StringMapModule titleExtractor=new StringMapModule(new ExtractTitleModule());
<<<<<<< HEAD
		ForwardIndexModuleInterface forwardTitleMod=new ForwardIndexModule(titleExtractor,"src/res/dataset/titleForwardIndexDataset");
=======
		ForwardIndexModuleInterface forwardTitleMod=new ForwardIndexModule(titleExtractor,"src/res/titleForwardIndexDataset");
>>>>>>> df1abd08a265cdec934beaa896a13047ca606782
		try {
			titleExtractor.generateKeywordStrMap("src/res/html/");
		}
		catch(Exception e) {
		}
	}
}
