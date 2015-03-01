using System.Collections.Generic;
using AgenceDTO;
using System.Data;


namespace AgenceDAO {

    public class AgendaDAO : DAOBase {

        internal AgendaDTO Mapper(IDataReader rd, AgendaDTO agenda) {
            agenda.Date = rd.GetDateTime("DATEENTREE");
            agenda.Description = rd.GetString("DESCRIPTION");
            agenda.Titre = rd.GetString("TITRE");
            if (rd.GetNullableInt("PROSPECTPERSONNEID") != null) {
                ProspectDAO prospect = new ProspectDAO();
                agenda.Prospect = prospect.Charger(rd.GetInt("PROSPECTPERSONNEID"));
            }
            if (rd.GetNullableInt("AGENTPERSONNEID") != null) {
                AgentDAO agent = new AgentDAO();
                agenda.Agent = agent.Charger(rd.GetInt("AGENTPERSONNEID"));
            }
            if (rd.GetNullableInt("ANNONCEID") != null) {
                AnnonceDAO annonce = new AnnonceDAO();
                agenda.Annonce = annonce.Charger(rd.GetInt("ANNONCEID"));
            }
            agenda.IdAgenda = rd.GetInt("ID");

            return agenda;
        }

        public virtual AgendaDTO Charger(int idAgenda) {
            _db.Sql = "SELECT ANNONCEID,PROSPECTPERSONNEID,AGENTPERSONNEID,DATEENTREE,TITRE,DESCRIPTION,ID FROM AGENDA"
                            + " WHERE ID=@idAgenda";
            _db.AddParameter("idAgenda", idAgenda);
            IDataReader rd = _db.ExecuteReader();
            AgendaDTO agenda = new AgendaDTO();
            try {
                if (rd.Read()) 
                    return Mapper(rd, agenda);
                else 
                    return null;
            }
            finally {
                rd.Close();
            }
        }

        public List<AgendaDTO> ChargerListeRendezVous() {
            _db.Sql = "SELECT ANNONCEID,PROSPECTPERSONNEID,AGENTPERSONNEID,DATEENTREE,TITRE,DESCRIPTION,ID FROM AGENDA";

            IDataReader rd = _db.ExecuteReader();
            List<AgendaDTO> Agendas = new List<AgendaDTO>();
            while (rd.Read()) {
                AgendaDTO Agenda = new AgendaDTO();
                Agendas.Add(Mapper(rd, Agenda));
            }
            rd.Close();
            return Agendas;
        }

        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            AgendaDTO agenda = (AgendaDTO)dto;
            int idAgenda = DBUtils.NouvelID(db, "Agenda");
            db.Sql = "INSERT INTO AGENDA (ID,ANNONCEID,PROSPECTPERSONNEID,AGENTPERSONNEID,DATEENTREE,TITRE,DESCRIPTION) " +
                                "VALUES (@id,@idAnnonce,@idProspect,@idAgent,@dateEntree,@titre,@description)";

            db.AddParameter("id", idAgenda);
            if (agenda.Annonce != null)
                db.AddParameter("idAnnonce", agenda.Annonce.IdAnnonce);
            else
                db.AddParameter("idAnnonce", null);
            if (agenda.Prospect != null)
                db.AddParameter("idProspect", agenda.Prospect.IdPersonne);
            else
                db.AddParameter("idProspect", null);
            db.AddParameter("idAgent", agenda.Agent.IdPersonne);
            db.AddParameter("dateEntree", agenda.Date);
            db.AddParameter("titre", agenda.Titre);
            db.AddParameter("description", agenda.Description);
            db.ExecuteNonQuery();

            return idAgenda;
        }

        public override void Supprimer(IDBWrapper db, int idAgenda) {
            db.Sql = "DELETE FROM AGENDA WHERE ID=@idAgenda";
            db.AddParameter("idAgenda", idAgenda);
            db.ExecuteNonQuery();
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }


        public bool VerifierSiBienDansAgenda(int idBien) {
            _db.Sql = "SELECT ID FROM AGENDA WHERE BIENID=@idBien";
            _db.AddParameter("idBien", idBien);
            IDataReader rd = _db.ExecuteReader();
            bool rslt = rd.Read();
            rd.Close();
            return rslt;
        }

    }
}
