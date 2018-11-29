package query;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import preprocess.Preprocess;
// TODO: Auto-generated Javadoc

/**
 * Main entrance of program.
 */
public class Main {
    
    /**
     * Approve.
     *
     * @param res the res
     * @return true, if successful
     */
    public static boolean approve(String res) {
        ArrayList<String> approval = new ArrayList<String>(){{
           add("y");
           add("yes");
        }};

        res = res.toLowerCase();

        return approval.contains(res);
    }

    /**
     * Prints the result.
     *
     * @param results the results
     * @param resultShow the result show
     */
    private static void printResult(ArrayList<String> results, int resultShow) {
        HashMap<String, String> titleIndex = new HashMap<>();
        // open the title dataset to read title of url
        try {
            // read inverted index
            FileInputStream in = new FileInputStream(new File("res/dataset/titleForwardIndexDataset"));
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
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
        // update web pages
		Scanner sc = new Scanner(System.in);
		do {
		System.out.print("Do you add new webpages?");
		if (approve(sc.nextLine())) {
			//System.out.println("What website you want to search?");
			Preprocess.setDomain("cs.cityu.edu.hk");
			Preprocess.generatePreprocess();
			System.out.println("New webpages added.");
		}

		// search keywords
		System.out.print("I want search:");
		String query = sc.nextLine();
		// search method
        System.out.print("Please select a ranking method (1 for tf-idf, 2 for pagerank)");
        String methodabb= sc.nextLine();
        String method;
        switch (methodabb) {
            case "1": method = "tfidf"; break;
            default: method = "pagerank"; break;
        }
		System.out.println("Search result for "+query+":");
		QueryInterface cq;
		try {
			cq=new CommonQuery();
			ArrayList<String> results=cq.search(query, method);
			int resultShow=(results.size()>10)?10:results.size();
			printResult(results, resultShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Do you want to search another keyword?");
		}while(!sc.nextLine().equals("no"));
		sc.close();
	}
}
