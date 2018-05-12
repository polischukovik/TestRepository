
public class Main {

	public static void main(String[] args) {
		int num = 11;
		if(num == 0) return;
		
		String placeholders = "?";
		for(int i = 0; i < num - 1; i++){
			placeholders += ", ?";
		}
		System.out.println(placeholders);
	}

}
