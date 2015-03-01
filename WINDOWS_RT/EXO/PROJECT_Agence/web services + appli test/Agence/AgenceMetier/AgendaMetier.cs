using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class AgendaMetier {

        public static void VerifierSaisie(AgendaDTO agenda) {
            if (agenda.Agent == null)
                throw new ExceptionMetier("Vous devez choisir l'agent immobilier concerné par le rendez-vous.");
            else if (agenda.Titre == string.Empty)
                throw new ExceptionMetier("Vous devez saisir le titre du rendez-vous.");
            else if (agenda.Date == null)
                throw new ExceptionMetier("Vous devez saisir la date du rendez-vous.");
        }


        public static void Ajouter(AgendaDTO agenda, UniteMetier um) {
            VerifierSaisie(agenda);
            AgendaDAO agendaDAO = new AgendaDAO();
            um.AjouterInsertion(agendaDAO, agenda);
        }


        public static List<AgendaDTO> ChargerListeRendezVous() {
            using (AgendaDAO agendaDAO = new AgendaDAO()) {
                return agendaDAO.ChargerListeRendezVous();
            }
        }

        public static AgendaDTO Charger(int idAgenda) {
            using (AgendaDAO agendaDAO = new AgendaDAO()) {
                AgendaDTO agenda = agendaDAO.Charger(idAgenda);
                if (agenda == null)
                    throw new ExceptionMetier("L'entrée d'agenda n'existe pas dans la base de données.");
                return agenda;
            }
        }

        public static void Supprimer(int idAgenda, UniteMetier um) {
            using (AgendaDAO agendaDAO = new AgendaDAO()) {
                AgendaDTO agenda = agendaDAO.Charger(idAgenda);
                if (agenda == null)
                    throw new ExceptionMetier("L'entrée d'agenda n'existe pas dans la base de données.");
                um.AjouterSuppression(agendaDAO, idAgenda);
            }
        }

    }
}
