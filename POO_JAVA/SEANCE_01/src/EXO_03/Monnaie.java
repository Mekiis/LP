package EXO_03;

import java.math.BigDecimal;

public class Monnaie {
	private static final float price = 0.1f;
	
    public static void main(String[] args) {
    	System.out.println(2.00 - 1.1);
    	
    	System.out.println(new BigDecimal("2.0").subtract(new BigDecimal("1.1")));
    	
    	float moneyFloat = 1.0f;
    	int nbCandy = 0;
    	while(moneyFloat - price * (nbCandy+1) > 0.0f){
    		nbCandy++;
    		moneyFloat -= price * nbCandy;
    	}
    	System.out.println("Money : "+moneyFloat+" / Candy : "+nbCandy);
    	
    	int moneyInt = 100;
    	nbCandy = 0;
    	while(moneyInt - price*100 * (nbCandy+1) > 0){
    		nbCandy++;
    		moneyInt -= price*100 * nbCandy;
    	}
    	System.out.println("Money : "+moneyInt/100f+" / Candy : "+nbCandy);
    	
    	BigDecimal moneyBD = new BigDecimal("1.0");
    	nbCandy = 0;
    	while(moneyBD.subtract(new BigDecimal(Float.toString(price)).multiply(new BigDecimal(Integer.toString(nbCandy+1)))).compareTo(new BigDecimal("0")) > 0){
    		nbCandy++;
    		moneyBD = moneyBD.subtract(new BigDecimal(Float.toString(price)).multiply(new BigDecimal(Integer.toString(nbCandy))));
    	}
    	System.out.println("Money : "+moneyBD+" / Candy : "+nbCandy);
    }
}
