using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService {
    public class AgendaService {

        public static List<AgendaDTO> ChargerListeRendezVous() {
            return AgendaMetier.ChargerListeRendezVous();
        }

        public static AgendaDTO Charger(int idAgenda) {
            return AgendaMetier.Charger(idAgenda);
        }

        public static String Ajouter(AgendaDTO agenda) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AgendaMetier.Ajouter(agenda, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idAgenda) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AgendaMetier.Supprimer(idAgenda, um);
            um.Executer();
            return rslt;
        }
    }
}
