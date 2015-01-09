package EXO_07;

public class AssertionExemple {

	public static void main(String[] args) {
		// Mettre -ea dans les variables de lancement de la VM
		maSuperMethod(4);
		maSuperMethod(-4);
	}
	
	public static void maSuperMethod(int i )
	{
		assert i > 0;
		System.out.println(i);
	}
}
