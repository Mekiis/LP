using System.Collections.Generic;
using AgenceDTO;
using System.Data;


namespace AgenceDAO {

    public class AnnonceDAO : DAOBase {

        internal AnnonceDTO Mapper(IDataReader rd, AnnonceDTO annonce) {
            annonce.Texte = rd.GetString("TEXTE");
            annonce.Titre = rd.GetString("TITRE");
            if (rd.GetNullableInt("BIENID") != null) {
                BienDAO bien = new BienDAO();
                annonce.Bien = bien.Charger(rd.GetInt("BIENID"));
            }
            annonce.Prix = rd.GetFloat("PRIX");
            annonce.IdAnnonce = rd.GetInt("ID");

            return annonce;
        }

        public virtual AnnonceDTO Charger(int idAnnonce) {
            _db.Sql = "SELECT ID,TITRE,TEXTE,BIENID,PRIX FROM ANNONCE"
                            + " WHERE ID=@idAnnonce";
            _db.AddParameter("idAnnonce", idAnnonce);
            IDataReader rd = _db.ExecuteReader();
            AnnonceDTO annonce = new AnnonceDTO();
            try {
                if (rd.Read()) 
                    return Mapper(rd, annonce);
                else 
                    return null;
            }
            finally {
                rd.Close();
            }
        }

        public List<AnnonceDTO> ChargerListeAnnonces() {
            _db.Sql = "SELECT ID,TITRE,TEXTE,BIENID,PRIX FROM ANNONCE";

            IDataReader rd = _db.ExecuteReader();
            List<AnnonceDTO> Annonces = new List<AnnonceDTO>();
            while (rd.Read()) {
                AnnonceDTO Annonce = new AnnonceDTO();
                Annonces.Add(Mapper(rd, Annonce));
            }
            rd.Close();
            return Annonces;
        }

        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            AnnonceDTO annonce = (AnnonceDTO)dto;
            int idAnnonce = DBUtils.NouvelID(db, "Annonce");
            db.Sql = "INSERT INTO ANNONCE (ID,TITRE,TEXTE,BIENID,PRIX) " +
                                "VALUES (@id,@titre,@texte,@idBien,@prix)";
            db.AddParameter("id", idAnnonce);
            db.AddParameter("titre", annonce.Titre);
            db.AddParameter("texte", annonce.Texte);
            db.AddParameter("idBien", annonce.Bien.IdBien);
            db.AddParameter("prix", annonce.Prix);
            db.ExecuteNonQuery();

            return idAnnonce;
        }

        public override void Supprimer(IDBWrapper db, int idAnnonce) {
            db.Sql = "DELETE FROM ANNONCE WHERE ID=@idAnnonce";
            db.AddParameter("idAnnonce", idAnnonce);
            db.ExecuteNonQuery();
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

        public bool VerifierSiBienDansAnnonce(int idBien) {
            _db.Sql = "SELECT ID FROM ANNONCE WHERE BIENID=@idBien";
            _db.AddParameter("idBien", idBien);
            IDataReader rd = _db.ExecuteReader();
            bool rslt = rd.Read();
            rd.Close();
            return rslt;
        }

    }
}
