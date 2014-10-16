package EXO_06;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Heure {

    public static void main(String[] args) {
       JFrame cadre = new JFrame();
       cadre.setLayout(new FlowLayout(FlowLayout.LEFT));
       JButton monBouton = new JButton("Quelle heure est-il ?");
       
       final JLabel monLabel = new JLabel();
       monBouton.addActionListener(new ActionListener(){
    	   @Override
          public void actionPerformed(ActionEvent e) {
    		   Calendar c = Calendar.getInstance();
             monLabel.setText(String.format("%tT", c));
    	   } 
       });
       
       cadre.add(monBouton);
       cadre.add(monLabel);
       cadre.setSize(300,60);
       cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       cadre.setVisible(true);
    } 
   }
