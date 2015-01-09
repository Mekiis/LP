import java.io.FileWriter;
import java.io.IOException;


public class Singleton {
	private static Singleton instance = new Singleton();
	private int count = 0;
	
	public static Singleton getInstance(){
		return instance;
	}

	public synchronized void increment(int pas){
		FileWriter writer = null;
		
		count += pas;
		
		try {
			String filename= "count.txt";
		    writer = new FileWriter(filename,true);
		    writer.write("Count : "+count+"\n");
		} catch (IOException ex) {
		  System.out.println("Error - While writing in file");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("Error - While closing file");
			}
		}		
	}
}
