using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService {
    public class AnnonceService {

        public static List<AnnonceDTO> ChargerListeAnnonces() {
            return AnnonceMetier.ChargerListeAnnonces();
        }

        public static AnnonceDTO Charger(int idAnnonce) {
            return AnnonceMetier.Charger(idAnnonce);
        }

        public static String Ajouter(AnnonceDTO annonce) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AnnonceMetier.Ajouter(annonce, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idAnnonce) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AnnonceMetier.Supprimer(idAnnonce, um);
            um.Executer();
            return rslt;
        }
    }
}
