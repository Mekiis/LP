using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService
{
    public class BienService
    {

        public static List<BienDTO> ChargerListeBiens(int idTypeBien = -1) {
            return BienMetier.ChargerListeBiens(idTypeBien);
        }

        public static BienDTO Charger(int idBien) {
            return BienMetier.Charger(idBien);
        }

        public static String Ajouter(BienDTO bien) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            BienMetier.Ajouter(bien, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idBien) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            BienMetier.Supprimer(idBien, um);
            um.Executer();
            return rslt;
        }
    }
}
