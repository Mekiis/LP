package EXO_07;


import javax.swing.JOptionPane;

class CalculAvecTryCatchFinally
{
  public static void main (String [] args)
  {
    String titre = "Calcul de factorielle"; // Titre des boites de dialogue
    int    reponse;
    String s = null;
    do
      try
      {
        s = JOptionPane.showInputDialog (null, "Entrez un nombre :",
                                         titre, JOptionPane.OK_CANCEL_OPTION);
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
        JOptionPane.showMessageDialog (null, s + " n'est pas un entier",
                                       titre, JOptionPane.ERROR_MESSAGE);
      }
      catch (IllegalArgumentException ex)
      {
        JOptionPane.showMessageDialog (null, ex.getMessage (),
                                       titre, JOptionPane.ERROR_MESSAGE);
      }
      finally
      {
        // Demande de confirmation pour quitter
        reponse = JOptionPane.showConfirmDialog (null, "Voulez-vous quitter ?",
                                                 titre, JOptionPane.YES_NO_OPTION);
      }
    while (reponse == JOptionPane.NO_OPTION);

    System.exit (0);
  }
}
