using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService {
    public class ProprietaireService {

        public static List<ProprietaireDTO> ChargerListeProprietaires() {
            return ProprietaireMetier.ChargerListeProprietaires();
        }

        public static ProprietaireDTO Charger(int idProprietaire) {
            return ProprietaireMetier.Charger(idProprietaire);
        }

        public static String Ajouter(ProprietaireDTO proprietaire) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            ProprietaireMetier.Ajouter(proprietaire, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idProprietaire) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            ProprietaireMetier.Supprimer(idProprietaire, um);
            um.Executer();
            return rslt;
        }
    }
}
