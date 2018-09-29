package preprocess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
	public static void main(String[] args) throws IOException {
		KeywordMapModule mockKeyword=new KeywordMapModule();
		ForwardIndexModuleInterface mockForward=new ForwardIndexModule(mockKeyword);
		InvertedIndexModuleInterface mockInvert=new InvertedIndexModule(mockForward);
		mockKeyword.generateKeywordStrMap("src/res/html");
		System.out.print("I want search:");
		Scanner scanner = new Scanner(System.in);
		String query = scanner.next();
		System.out.println("Search result for "+query+":");
		System.out.println(mockInvert.getInvertedIndexMap().get(query.toLowerCase()));
		
	}
}
