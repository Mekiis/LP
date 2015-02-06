using System.Collections.Generic;
using AgenceDTO;
using System.Data;

namespace AgenceDAO {

    public class AgentDAO : PersonneDAO {

        internal AgentDTO Mapper(IDataReader rd, AgentDTO agent) {
            base.Mapper(rd, agent);
            agent.MotDePasse = rd.GetString("MOTDEPASSE");
            agent.Login = rd.GetString("LOGIN");
            return agent;
        }

        public new AgentDTO Charger(int idAgent) {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,PERSONNE.ID,LOGIN,MOTDEPASSE FROM PERSONNE"
                            + " INNER JOIN AGENT ON PERSONNEID=ID"
                            + " WHERE ID=@idAgent";
            _db.AddParameter("idAgent", idAgent);
            IDataReader rd = _db.ExecuteReader();
            AgentDTO agent = new AgentDTO();
            try {
                if (rd.Read()) 
                    return Mapper(rd, agent);
                else 
                    return null;
            }
            finally {
                rd.Close();
            }
        }

        public List<AgentDTO> ChargerListeAgents() {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,PERSONNE.ID,LOGIN,MOTDEPASSE FROM PERSONNE"
                            + " INNER JOIN AGENT ON PERSONNEID=ID";
            IDataReader rd = _db.ExecuteReader();
            List<AgentDTO> agents = new List<AgentDTO>();
            while (rd.Read()) {
                AgentDTO agent = new AgentDTO();
                agents.Add(Mapper(rd, agent));
            }
            rd.Close();
            return agents;
        }

        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            AgentDTO agent = (AgentDTO)dto;
            int idPersonne = base.Ajouter(db, agent);
            db.Sql = "INSERT INTO AGENT (PERSONNEID,LOGIN,MOTDEPASSE) " +
                                "VALUES (@idPersonne,@login,@motdepasse)";

            db.AddParameter("idPersonne", idPersonne);
            db.AddParameter("login", agent.Login);
            db.AddParameter("motdepasse", agent.MotDePasse);
            db.ExecuteNonQuery();

            return idPersonne;
        }

        public override void Supprimer(IDBWrapper db, int idAgent) {
            db.Sql = "DELETE FROM AGENT WHERE PERSONNEID=@idAgent";
            db.AddParameter("idAgent", idAgent);
            db.ExecuteNonQuery();
            base.Supprimer(db, idAgent);
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

    }
}
