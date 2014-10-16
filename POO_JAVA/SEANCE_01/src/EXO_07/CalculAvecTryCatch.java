package EXO_07;


import javax.swing.JOptionPane;

class CalculAvecTryCatch
{
  public static void main (String [] args)
  {
    String s = null;
    do
      try
      {
        s = JOptionPane.showInputDialog ("Entrez un nombre :");
        if (s != null)
        {
          // La conversion du texte saisi en nombre peut declencher
          // une exception de classe java.lang.NumberFormatException
          long n = Long.parseLong (s);
          long factN = Calcul.factorielle (n);
          JOptionPane.showMessageDialog (null, n + "! = " + factN);
        }
      }
      catch (NumberFormatException ex)
      {
        // ex.getMessage() contient la chaine que parseLong n'a pas pu convertir
        JOptionPane.showMessageDialog (null, s + " n'est pas un entier");
      }
    while (s != null);
  }
}
