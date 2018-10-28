package query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import preprocess.Preprocess;
public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you add new webpages?");
		if (sc.nextLine().toLowerCase().equals("y")) {
			Preprocess.generatePreprocess();
			System.out.println("New webpages added.");
		}
		System.out.print("I want search:");
		String query = sc.nextLine();
		ArrayList<String> queryList=new ArrayList<String>(Arrays.asList(query.split(" ")));
		System.out.println("Search result for "+query+":");
		QueryInterface cq;
		try {
			cq=new CommonQuery();
			ArrayList<String> results=cq.search(queryList);
			int resultShow=(results.size()>10)?10:results.size();
			for (int i=0;i<resultShow;i++) {
				System.out.println(results.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
}
