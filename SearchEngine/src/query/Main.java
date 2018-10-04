package query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import preprocess.Preprocess;
public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you add new webpages?");
		if (sc.nextLine()=="Y") {
			Preprocess.generatePreprocess();
		}	
		System.out.print("I want search:");
		String query = sc.nextLine().toLowerCase();
		ArrayList<String> queryList=new ArrayList<String>(Arrays.asList(query.split(" ")));
		System.out.println("Search result for "+query+":");
		QueryInterface cq=new CommonQuery();
		ArrayList<String> results=cq.search(queryList);
		for (String item:results) {
			System.out.println(item);
		}
		sc.close();
	}
}
