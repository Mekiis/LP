using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;
using AgenceUniteMetier;

namespace AgenceService {
    public class AgentService {

        public static List<AgentDTO> ChargerListeAgents() {
            return AgentMetier.ChargerListeAgents();
        }

        public static AgentDTO Charger(int idAgent) {
            return AgentMetier.Charger(idAgent);
        }

        public static String Ajouter(AgentDTO agent) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AgentMetier.Ajouter(agent, um);
            um.Executer();
            return rslt;
        }

        public static String Supprimer(int idAgent) {
            String rslt = String.Empty;
            UniteMetier um = new UniteMetier();
            AgentMetier.Supprimer(idAgent, um);
            um.Executer();
            return rslt;
        }
    }
}
