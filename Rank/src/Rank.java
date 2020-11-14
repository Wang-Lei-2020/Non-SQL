
public class Rank {
	
	
	public static String[] reRank(String[] input) {
		
		int r = 0,b = 0,g = 0;
		
		for(int i = 0;i<input.length;i++) {
			if(input[i].equals("r")) {
				r++;
			}else if(input[i].equals("g")) {
				g++;
			}else if(input[i].equals("b")) {
				b++;
			}
		}
		
		String[] str = new String[input.length];
		for(int i =0;i<r;i++) {
			str[i] = "r";
		}
		for(int i =r;i<r+g;i++) {
			str[i] = "g";
		}
		for(int i =r+g;i<r+g+b;i++) {
			str[i] = "b";
		}
		
		return str;
	}
	
	
	public static String[] reRankTwoPointer(String[] input){
		
		if(input == null || input.length == 0) {
			return new String[0];
		}
		
		int i= 0,left = 0;
		int right = input.length-1;
		while(i<right+1) {
			if(input[i].equals("r")) {
				String t1 = input[i];
				input[i] = input[left];
				input[left] = t1;
				i++;
				left++;
			}
			else if(input[i].equals("g")) {
				i++;
			}else if(input[i].equals("b")) {
				String t2= input[i];
				input[i] = input[right];
				input[right] = t2;
				right--;
			}
		}
		
		return input;
		
	}
	

	public static void main(String[] args) {
		String[] input = new String[] {"r","r","b","g","b","r","g","g","b"};
		String[] str = new String[input.length];
		String []str1 = new String[input.length];
		str1 = reRank(input);
		str = reRankTwoPointer(input);
		
		for(int i =0;i<input.length;i++) {
			System.out.print(str[i]);
		}
		
		System.out.println();
		
		for(int i =0;i<input.length;i++) {
			System.out.print(str1[i]);
		}
		
	}
}
