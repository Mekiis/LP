using System.Collections.Generic;
using AgenceDTO;
using System.Data;

namespace AgenceDAO
{
    public class BienDAO : DAOBase 
    {

        internal BienDTO Mapper(IDataReader rd, BienDTO bien) {
            bien.IdBien = rd.GetInt("BIENID");
            bien.Description = rd.GetString("DESCRIPTION");
            bien.Latitude = rd.GetString("LATITUDE");
            bien.Longitude = rd.GetString("LONGITUDE");
            bien.Titre = rd.GetString("TITRE");
            //on fait volontairement le choix de ne pas charger le propriétaire ; il sera chargé à la demande.
            bien.Proprietaire = null;
            bien.Adresse = rd.GetString("ADRESSE");
            TypeBienDAO.Mapper(rd, bien);
            return bien;
        }


        public BienDTO Charger(int idBien) {
            _db.Sql = "SELECT BIEN.ID BIENID,TITRE,DESCRIPTION,TYPEBIENID,PROPRIETAIREID,LATITUDE,LONGITUDE,ADRESSE,LIBELLE FROM BIEN"
                            + " INNER JOIN TYPEBIEN ON BIEN.TYPEBIENID=TYPEBIEN.ID"
                            + " WHERE BIEN.ID=@idBien";
            _db.AddParameter("idBien", idBien);
            IDataReader rd = _db.ExecuteReader();
            BienDTO bien = new BienDTO();
            try {
                if (rd.Read())
                    return Mapper(rd, bien);
                else
                    return null;
            }
            finally {
                rd.Close();
            }
        }


        public List<BienDTO> ChargerListeBiens(int idTypeBien = -1) {
            _db.Sql = "SELECT BIEN.ID BIENID,TITRE,DESCRIPTION,TYPEBIENID,PROPRIETAIREID,LATITUDE,LONGITUDE,ADRESSE,LIBELLE FROM BIEN"
                            + " INNER JOIN TYPEBIEN ON BIEN.TYPEBIENID=TYPEBIEN.ID";

            if (idTypeBien != -1) {
                _db.Sql += " WHERE TYPEBIENID=@idTypeBien";
                _db.AddParameter("idTypeBien", idTypeBien);
            }

            IDataReader rd = _db.ExecuteReader();
            List<BienDTO> biens = new List<BienDTO>();
            while (rd.Read()) {
                BienDTO bien = new BienDTO();
                biens.Add(Mapper(rd, bien));
            }
            rd.Close();
            return biens;
        }


        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) {
            BienDTO bien = (BienDTO)dto;
            int idBien = DBUtils.NouvelID(db, "Bien");
            db.Sql = "INSERT INTO BIEN (ID,TYPEBIENID,TITRE,DESCRIPTION,ADRESSE,LATITUDE,LONGITUDE) " +
                                "VALUES (" +
                                "@IDBIEN,@TYPEBIENID,@TITRE,@DESCRIPTION,@ADRESSE,@LATITUDE,@LONGITUDE)";

            db.AddParameter("idBien", idBien);
            db.AddParameter("typebienID", bien.IdTypeBien);
            db.AddParameter("titre", bien.Titre);
            db.AddParameter("description", bien.Description);
            db.AddParameter("adresse", bien.Adresse);
            db.AddParameter("latitude", bien.Latitude);
            db.AddParameter("longitude", bien.Longitude);
            db.ExecuteNonQuery();

            return idBien;
        }


        public override void Supprimer(IDBWrapper db, int idBien) {
            db.Sql = "DELETE FROM BIEN WHERE ID=@idBien";
            db.AddParameter("idBien", idBien);
            db.ExecuteNonQuery();
        }

        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }

    }
}
