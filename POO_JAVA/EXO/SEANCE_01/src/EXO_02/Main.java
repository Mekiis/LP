package EXO_02;

public class Main {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
				
		// Time : 9.X s
		//Long sum = 0L;
		// Time : 1.X s
		long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i; 
		}
		
		System.out.println((System.currentTimeMillis() - startTime) / 1000f);
		
		Integer a = Integer.valueOf(12);
		Integer b = Integer.valueOf(12);
		
		System.out.println(a==b);
		System.out.println(a.equals(b));
		
		b += 1;
		System.out.println(a);
		System.out.println(b);
    }
	
}
