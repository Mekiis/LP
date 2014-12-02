using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class AgentMetier {

        public static void VerifierSaisie(AgentDTO agent) {
            PersonneMetier.VerifierSaisie(agent);
        }


        public static void Ajouter(AgentDTO agent, UniteMetier um) {
            VerifierSaisie(agent);
            AgentDAO agentDAO = new AgentDAO();
            um.AjouterInsertion(agentDAO, agent);
        }


        public static List<AgentDTO> ChargerListeAgents() {
            using (AgentDAO agentDAO = new AgentDAO()) {
                return agentDAO.ChargerListeAgents();
            }
        }

        public static AgentDTO Charger(int idAgent) {
            using (AgentDAO agentDAO = new AgentDAO()) {
                AgentDTO agent = agentDAO.Charger(idAgent);
                if (agent == null)
                    throw new ExceptionMetier("L'agent n'existe pas dans la base de données.");
                return agent;
            }
        }

        public static void Supprimer(int idAgent, UniteMetier um) {
            // TODO: vérifier si le Agent à supprimer existe
            AgentDAO agentDAO = new AgentDAO();
            um.AjouterSuppression(agentDAO, idAgent);
        }

    }
}
