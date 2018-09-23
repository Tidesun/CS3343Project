package preprocess;

public class Main {
	public static void main(String[] args) {
		InvertedIndexModule mock=new InvertedIndexModule();
		System.out.println(mock.getInvertedIndexMap().get("b"));
	}
}
