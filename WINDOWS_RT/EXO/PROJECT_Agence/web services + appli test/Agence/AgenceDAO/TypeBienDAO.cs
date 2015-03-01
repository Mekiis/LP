using System;
using System.Collections.Generic;
using AgenceDTO;
using System.Data;

namespace AgenceDAO {

    public class TypeBienDAO : DAOBase {

        //internal : visible qu'au sein du package
        internal static TypeBienDTO Mapper(IDataReader rd, TypeBienDTO typeBien) {
            typeBien.IdTypeBien = rd.GetInt("TYPEBienID");
            typeBien.LibelleType = rd.GetString("LIBELLE");
            return typeBien;
        }

        public List<TypeBienDTO> ChargerListeTypesBiens() {
            String sql = "SELECT ID TYPEBienID,LIBELLE FROM TYPEBien";
            _db.Sql = sql;
            IDataReader rd = _db.ExecuteReader();

            List<TypeBienDTO> typesBiens = new List<TypeBienDTO>();
            while (rd.Read()) {
                TypeBienDTO typeBien = new TypeBienDTO();
                typesBiens.Add(Mapper(rd, typeBien));
            }
            rd.Close();
            return typesBiens;
        }

        public override int Ajouter(IDBWrapper db, IAgenceDTO dto) { return 0; }
        public override void Modifier(IDBWrapper db, IAgenceDTO dto) { }
        public override void Supprimer(IDBWrapper db, int idToDelete) { }
    }
}
