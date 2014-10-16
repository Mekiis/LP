package EXO_05;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		float mass = Float.parseFloat(JOptionPane.showInputDialog("Votre masse sur Terre : "));
		
		Planet[] planets = Planet.values();
		
		String s = "";
		for(int i = 0; i < planets.length; i++){
			s += "Poids sur "+planets[i]+" : "+planets[i].surfaceWeight(mass)/Planet.TERRE.surfaceGravity()+"\n";
		}
		
		JOptionPane.showMessageDialog(null, s);
	}
}
