using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService {
    public class ProspectService {

        public static List<ProspectDTO> ChargerListeProspects() {
            return ProspectMetier.ChargerListeProspects();
        }

        public static ProspectDTO Charger(int idProspect) {
            return ProspectMetier.Charger(idProspect);
        }

        public static String Ajouter(ProspectDTO prospect) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            ProspectMetier.Ajouter(prospect, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idProspect) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            ProspectMetier.Supprimer(idProspect, um);
            um.Executer();
            return rslt;
        }
    }
}
