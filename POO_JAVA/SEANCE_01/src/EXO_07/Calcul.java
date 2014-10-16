package EXO_07;



public class Calcul
{
  /**
   * Renvoie la factorielle de n, egale a n x (n - 1) x (n - 2) x ... x 2 x 1
   * @exception IllegalArgumentException si n < 0 ou n > 20
   */
  public static long factorielle (long n)
  {
    // Verification des cas limites
    if (n < 0 || n > 20)
      throw new IllegalArgumentException (n + " n'est pas entre 0 et 20");
    // Condition d'arret
    if (n <= 1)
      return 1;
    else
      // Appel recursif a factorielle
      return n * factorielle (n - 1);
  }
}
