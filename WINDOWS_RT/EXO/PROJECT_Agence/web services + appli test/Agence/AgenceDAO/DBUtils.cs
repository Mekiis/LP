using System.Data;

namespace AgenceDAO {

    public class DBUtils {

        public static int NouvelID(IDBWrapper dbWrapper, string table) {
            table = table.ToUpper();

            dbWrapper.Sql = "select valeur from clef where nom_table=@table";
            dbWrapper.AddParameter("table", table);

            int retour;

            IDataReader rd = dbWrapper.ExecuteReader();

            if (rd.Read()) {
                retour = rd.GetInt16(0) + 1;
                rd.Close();
                dbWrapper.Sql = "update clef set valeur=@valeur where nom_table=@table";
                dbWrapper.AddParameter("valeur", retour);
                dbWrapper.AddParameter("table", table);
                dbWrapper.ExecuteNonQuery();
            }
            else {
                retour = 1;
                rd.Close();
                dbWrapper.Sql = "insert into clef(nom_table,valeur) values(@table,@valeur)";
                dbWrapper.AddParameter("valeur", retour);
                dbWrapper.AddParameter("table", table);
                dbWrapper.ExecuteNonQuery();
            }
            return retour;
        }
    }
}
