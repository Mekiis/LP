using System.Collections.Generic;
using AgenceDTO;
using System.Data;

namespace AgenceDAO {

    public class ProprietaireDAO : PersonneDAO {

        internal ProprietaireDTO Mapper(IDataReader rd, ProprietaireDTO proprietaire) {
            base.Mapper(rd, proprietaire);
            proprietaire.Adresse = rd.GetString("ADRESSE");
            return proprietaire;
        }


        public new ProprietaireDTO Charger(int idProprietaire) {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,ADRESSE,PERSONNE.ID FROM PERSONNE"
                            + " INNER JOIN PROPRIETAIRE ON PERSONNEID=ID"
                            + " WHERE ID=@idProprietaire";
            _db.AddParameter("idProprietaire", idProprietaire);
            IDataReader rd = _db.ExecuteReader();
            ProprietaireDTO proprietaire = new ProprietaireDTO();
            try {
                if (rd.Read())
                    return Mapper(rd, proprietaire);
                else
                    return null;
            }
            finally {
                rd.Close();
            }
        }


        public List<ProprietaireDTO> ChargerListeProprietaires() {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE,ADRESSE,PERSONNE.ID FROM PERSONNE"
                            + " INNER JOIN PROPRIETAIRE ON PERSONNEID=ID";
            IDataReader rd = _db.ExecuteReader();
            List<ProprietaireDTO> proprietaires = new List<ProprietaireDTO>();
            while (rd.Read()) {
                ProprietaireDTO proprietaire = new ProprietaireDTO();
                proprietaires.Add(Mapper(rd, proprietaire));
            }
            rd.Close();
            return proprietaires;
        }


        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            ProprietaireDTO proprietaire = (ProprietaireDTO)dto;
            int idPersonne = base.Ajouter(db,proprietaire);
            db.Sql = "INSERT INTO PROPRIETAIRE (ADRESSE,PERSONNEID) " +
                                "VALUES (@adresse,@idPersonne)";

            db.AddParameter("idPersonne", idPersonne);
            db.AddParameter("adresse", proprietaire.Adresse);
            db.ExecuteNonQuery();

            return idPersonne;
        }


        public override void Supprimer(IDBWrapper db, int idProprietaire) {
            db.Sql = "DELETE FROM PROPRIETAIRE WHERE PERSONNEID=@idProprietaire";
            db.AddParameter("idProprietaire", idProprietaire);
            db.ExecuteNonQuery();
            base.Supprimer(db, idProprietaire);
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

    }
}
