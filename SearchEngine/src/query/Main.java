package query;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import preprocess.Preprocess;
public class Main {
    public static boolean approve(String res) {
        ArrayList<String> approval = new ArrayList<String>(){{
           add("y");
           add("yes");
        }};

        res = res.toLowerCase();

        return approval.contains(res);
    }

    private static void printResult(ArrayList<String> results, int resultShow) {
        HashMap<String, String> titleIndex = new HashMap<>();
        // open the title dataset to read title of url
        try {
            // read inverted index
            FileInputStream in = new FileInputStream(new File("src/res/dataset/titleForwardIndexDataset"));
            ObjectInputStream input = new ObjectInputStream(in);
            titleIndex = (HashMap<String, String>) input.readObject();
            input.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // print formated single url
        for (int i=0; i<resultShow; i++) {
            System.out.print((i+1) + ". ");
            System.out.println(titleIndex.get(results.get(i)));
            System.out.println(results.get(i));
            System.out.println();
        }
    }
	public static void main(String[] args) throws IOException {
        // update web pages
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you add new webpages?");
		if (approve(sc.nextLine())) {
			Preprocess.generatePreprocess();
			System.out.println("New webpages added.");
		}

		// search keywords
		System.out.print("I want search:");
		String query = sc.nextLine();
		System.out.println("Search result for "+query+":");
		QueryInterface cq;
		try {
			cq=new CommonQuery();
			ArrayList<String> results=cq.search(query);
			int resultShow=(results.size()>10)?10:results.size();
			printResult(results, resultShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
}
