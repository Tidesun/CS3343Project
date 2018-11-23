package preprocess;
import java.io.IOException;
// TODO: Auto-generated Javadoc

/**
 * Main function of preprocess module.
 */
public class Preprocess {
	protected static String domain;
	public static void setDomain(String _domain){
		domain=_domain;
	}
	/**
	 * Generate keywords map, url map and web page title map.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void generatePreprocess() throws IOException {
		/* body element keywords extractor module*/
		StringMapModule bodyExtractor=new StringMapModule(new ExtractBodyModule());
		ForwardIndexModuleInterface forwardBodyMod=new ForwardIndexModule(bodyExtractor,"res/dataset/ForwardIndexDataset");
		InvertedIndexModuleInterface invertedBodyMod=new InvertedIndexModule(forwardBodyMod,"res/dataset/InvertedIndexDataset");
		bodyExtractor.generateKeywordStrMap("res/html/");
		
		/* url extractor module*/
		StringMapModule urlExtractor=new StringMapModule(new ExtractLinkModule());
		ForwardIndexModuleInterface forwardLinkMod=new ForwardIndexModule(urlExtractor,"res/dataset/linkForwardIndexDataset");
		InvertedIndexModuleInterface invertedLinkMod=new InvertedIndexModule(forwardLinkMod,"res/dataset/linkInvertedIndexDataset");
		urlExtractor.generateKeywordStrMap("res/html/");
		
		/* title extractor module*/
		StringMapModule titleExtractor=new StringMapModule(new ExtractTitleModule(),"res/dataset/titleForwardIndexDataset");
		try {
			titleExtractor.generateKeywordStrMap("res/html/");
		}
		catch(Exception e) {
		}
	}
}
