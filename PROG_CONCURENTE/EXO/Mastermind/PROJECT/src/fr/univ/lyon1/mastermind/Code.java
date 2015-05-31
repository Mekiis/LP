package fr.univ.lyon1.mastermind;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Immutable class
public class Code {
	private final Peg[] code;
	
	private Code(Peg[] code) {
		//Defensive copy
		this.code = code;
	}
	
	public static Code valueOf(Peg... code){
		return new Code(Arrays.copyOf(code, code.length));
	}

	public Peg[] asArray() {
		//Defensive copy
		return Arrays.copyOf(code, code.length);
	}
	
	public List<Peg> asList() {
		return Collections.unmodifiableList(Arrays.asList(code));
	}
	
	public Code nextCode() {
		boolean carry;
		Peg[] colors = Peg.values();
		int colorCount = colors.length;
		//Create a copy of the array
		Peg[] nextCode = asArray();
		
		int digit=0;
		do{
			int colorValue = nextCode[digit].ordinal();
			if(colorValue < (colorCount - 1)) {
				//We haven't reached the last color
				colorValue++;
				carry = false;
			}else{
				//Restart from the beginning color
				colorValue = 0;
				//Update next color
				carry = true;
			}
			nextCode[digit] = colors[colorValue];
			digit++;
		}while(digit < code.length && carry);
		
		
		return new Code(nextCode);
	}

	public int length() {
		return code.length;
	}

	@Override
	public String toString() {
		return Arrays.toString(code);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(code);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Code))
			return false;
		Code other = (Code) obj;
		if (!Arrays.equals(code, other.code))
			return false;
		return true;
	}

	int[] countColors(){
		Peg[] codeArray = asArray();
		Peg[] allColors = Peg.values();
		int[] counts = new int[allColors.length];
		
		for (int i = 0; i < allColors.length; i++) {
			Peg color = allColors[i];
			for (int j = 0; j < codeArray.length; j++) {
				if(codeArray[j] == color){
					counts[i]++;
				}
				
			}
		}
		
		return counts;
	}
	
}
