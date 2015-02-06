using System.Collections.Generic;
using AgenceDTO;
using System.Data;


namespace AgenceDAO {

    public class PersonneDAO : DAOBase {

        internal PersonneDTO Mapper(IDataReader rd, PersonneDTO personne) {
            personne.Nom = rd.GetString("NOM");
            personne.Prenom = rd.GetString("PRENOM");
            personne.Telephone = rd.GetString("TELEPHONE");
            personne.IdPersonne = rd.GetInt("ID");
            return personne;
        }


        public virtual PersonneDTO Charger(int idPersonne) {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE FROM PERSONNE"
                            + " WHERE ID=@idPersonne";
            _db.AddParameter("idPersonne", idPersonne);
            IDataReader rd = _db.ExecuteReader();
            PersonneDTO personne = new PersonneDTO();
            try {
                if (rd.Read())
                    return Mapper(rd, personne);
                else
                    return null;
            }
            finally {
                rd.Close();
            }
        }


        public List<PersonneDTO> ChargerListePersonnes() {
            _db.Sql = "SELECT PERSONNE.NOM,PERSONNE.PRENOM,PERSONNE.TELEPHONE FROM PERSONNE";

            IDataReader rd = _db.ExecuteReader();
            List<PersonneDTO> Personnes = new List<PersonneDTO>();
            while (rd.Read()) {
                PersonneDTO personne = new PersonneDTO();
                Personnes.Add(Mapper(rd, personne));
            }
            rd.Close();
            return Personnes;
        }


        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            PersonneDTO personne = (PersonneDTO)dto;
            int idPersonne = DBUtils.NouvelID(db, "Personne");
            db.Sql = "INSERT INTO PERSONNE (ID,NOM,PRENOM,TELEPHONE) " +
                                "VALUES (@ID,@NOM,@PRENOM,@TELEPHONE)";

            db.AddParameter("id", idPersonne);
            db.AddParameter("nom", personne.Nom);
            db.AddParameter("prenom", personne.Prenom);
            db.AddParameter("telephone", personne.Telephone);
            db.ExecuteNonQuery();

            return idPersonne;
        }


        public override void Supprimer(IDBWrapper db, int idPersonne) {
            db.Sql = "DELETE FROM PERSONNE WHERE ID=@idPersonne";
            db.AddParameter("idPersonne", idPersonne);
            db.ExecuteNonQuery();
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

    }
}
