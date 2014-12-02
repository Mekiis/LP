using System;
using System.Data;
using System.Data.SQLite;
using AgenceUtils;

namespace AgenceDAO {
    /// <summary>
    /// IBDWrapper
    /// </summary>
    public interface IDBWrapper : IDisposable {

        String Sql { get; set; }

        void AddParameter(String Column, object Value);

        IDataReader ExecuteReader();

        int ExecuteNonQuery();

        void BeginTransaction();

        void Commit();

        void Rollback();

        void Close();
    }


    public static class WrapperFactory {
        public static IDBWrapper CreerWrapper(VariablesGlobales.TypeConnection Base, String ConnectionString) {
            switch (Base) {
                case VariablesGlobales.TypeConnection.SQLite:
                    return new SQLiteWrapper(ConnectionString);

                default:
                    throw new ExceptionDAO("Type de base de données non géré.");
            }
        }
    }


    /// <summary>
    /// SQLiteWrapper
    /// </summary>
    public class SQLiteWrapper : IDBWrapper, IDisposable {
        private SQLiteConnection _cnx;
        private String _pathBase;
        private SQLiteCommand _command;
        private SQLiteTransaction _tr;

        public String Sql {
            set {
                _command.CommandText = value;
            }
            get {
                return _command.CommandText;
            }
        }

        public SQLiteWrapper(String cheminBase) {
            _pathBase = cheminBase;
            _pathBase = _pathBase.Replace(@"\", @"/");
            //connection pooling avec ADO.Net : http://msdn.microsoft.com/en-us/library/8xx3tyca%28v=vs.71%29.aspx
            _cnx = new SQLiteConnection("Data Source=" + _pathBase + ";Version=3");
            _cnx.Open();
            _command = new SQLiteCommand(_cnx);
        }

        public void AddParameter(String colonne, object valeur) {
            if (valeur == null)
                _command.Parameters.AddWithValue(colonne, DBNull.Value);
            else
                _command.Parameters.AddWithValue(colonne, valeur);
        }

        public IDataReader ExecuteReader() {
            IDataReader rslt;
            try {
                if (_command.CommandText == String.Empty)
                    throw new Exception("Aucune requête SQL à executer.");

                rslt = _command.ExecuteReader();
            }
            catch (Exception e) {
                throw new ExceptionDAO(e.Message, _command.CommandText, e);
            }
            return rslt;
        }

        public int ExecuteNonQuery() {
            try {
                _command.Transaction = _tr;
                return _command.ExecuteNonQuery();
            }
            catch (Exception e) {
                throw new ExceptionDAO(e.Message, _command.CommandText, e);
            }
        }

        public void BeginTransaction() {
            _tr = _cnx.BeginTransaction();
        }

        public void Commit() {
            _tr.Commit();
            _tr = null;
        }

        public void Rollback() {
            if (_tr != null)
                _tr.Rollback();
            _tr = null;
        }

        public void Close() {
            _cnx.Close();
        }

        public void Dispose() {
            if (_tr != null) {
                _tr.Dispose();
            }
            _command.Dispose();
            _cnx.Close();
            _cnx.Dispose();
            GC.SuppressFinalize(this);
        }
    }

    /// <summary>
    /// Quelques méthodes d'extension pour le DataReader...
    /// </summary>
    public static class ExtendedDataReader {

        public static int? GetNullableInt(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            if (!rd.IsDBNull(i))
                return rd.GetInt32(i);
            else
                return null;
        }

        /// <summary>
        /// Retourne l'entier à partir du champ.
        /// </summary>
        /// <remarks>Attention, si l'entier est null en base, une exception sera levée.</remarks>
        /// <param name="Champ"></param>
        /// <returns></returns>
        public static int GetInt(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            return rd.GetInt32(i);
        }

        public static String GetString(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            if (!rd.IsDBNull(i))
                return rd.GetString(i);
            else
                return null;
        }

        public static DateTime? GetDateTime(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            if (!rd.IsDBNull(i))
                return rd.GetDateTime(i);
            else
                return null;
        }

        public static float GetFloat(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            return rd.GetFloat(i);
        }

        public static float? GetNullableFloat(this IDataReader rd, String Champ) {
            int i = rd.GetOrdinal(Champ);
            if (!rd.IsDBNull(i))
                return rd.GetFloat(i);
            else
                return null;
        }
    }
}
