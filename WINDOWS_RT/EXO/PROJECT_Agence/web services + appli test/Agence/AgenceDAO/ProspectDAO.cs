using System.Collections.Generic;
using AgenceDTO;
using System.Data;

namespace AgenceDAO {

    public class ProspectDAO : PersonneDAO {

        internal ProspectDTO Mapper(IDataReader rd, ProspectDTO prospect) {
            base.Mapper(rd, prospect);
            prospect.DateContact = rd.GetDateTime("DATECONTACT");
            return prospect;
        }


        public new ProspectDTO Charger(int idProspect) {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,DATECONTACT,PERSONNE.ID FROM PERSONNE"
                            + " INNER JOIN PROSPECT ON PERSONNEID=ID"
                            + " WHERE ID=@idProspect";
            _db.AddParameter("idProspect", idProspect);
            IDataReader rd = _db.ExecuteReader();
            ProspectDTO prospect = new ProspectDTO();
            try {
                if (rd.Read())
                    return Mapper(rd, prospect);
                else
                    return null;
            }
            finally {
                rd.Close();
            }
        }


        public List<ProspectDTO> ChargerListeProspects() {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,DATECONTACT,PERSONNE.ID FROM PERSONNE"
                            + " INNER JOIN PROSPECT ON PERSONNEID=ID";
            IDataReader rd = _db.ExecuteReader();
            List<ProspectDTO> prospects = new List<ProspectDTO>();
            while (rd.Read()) {
                ProspectDTO prospect = new ProspectDTO();
                prospects.Add(Mapper(rd, prospect));
            }
            rd.Close();
            return prospects;
        }


        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            ProspectDTO prospect = (ProspectDTO)dto;
            int idPersonne = base.Ajouter(db, prospect);
            db.Sql = "INSERT INTO PROSPECT (DATECONTACT,PERSONNEID) " +
                                "VALUES (@adresse,@idPersonne)";

            db.AddParameter("idPersonne", idPersonne);
            db.AddParameter("adresse", prospect.DateContact);
            db.ExecuteNonQuery();

            return idPersonne;
        }


        public override void Supprimer(IDBWrapper db, int idProspect) {
            db.Sql = "DELETE FROM PROSPECT WHERE PERSONNEID=@idProspect";
            db.AddParameter("idProspect", idProspect);
            db.ExecuteNonQuery();
            base.Supprimer(db, idProspect);
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

    }
}
