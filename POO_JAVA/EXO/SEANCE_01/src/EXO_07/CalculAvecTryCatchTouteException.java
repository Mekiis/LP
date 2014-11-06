package EXO_07;




import javax.swing.JOptionPane;

class CalculAvecTryCatchTouteException
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
          // L'appel a factorielle peut declencher
          // une exception de classe java.lang.IllegalArgumentException
          long factN = Calcul.factorielle (n);
          JOptionPane.showMessageDialog (null, n + "! = " + factN);
        }
      }
      catch (NumberFormatException ex)
      {
        JOptionPane.showMessageDialog (null, s + " n'est pas un entier");
      }
      catch (IllegalArgumentException ex)
      {
        JOptionPane.showMessageDialog (null, ex.getMessage ());
      }
    while (s != null);
  }
}
