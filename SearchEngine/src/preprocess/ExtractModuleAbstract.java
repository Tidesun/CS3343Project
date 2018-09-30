package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExtractModuleAbstract {
	protected String getContent(File filename) throws FileNotFoundException {
		Scanner sc =new Scanner(filename);
		String content="";
		while(sc.hasNext()){
			content +=sc.nextLine()+"\n";
		}
		sc.close();
		return content;
	}
}
