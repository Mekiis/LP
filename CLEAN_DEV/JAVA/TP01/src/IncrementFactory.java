
public class IncrementFactory {
	 //use getShape method to get object of type shape 
	public IIncrement getIncrement(int incPas){
		IIncrement inc = null;
		
      if(incPas == 1){
    	  inc = new Inc1();
      }
      
      return null;
   }
}
