using AgenceDTO;
using System.Data;
using System;
using AgenceUtils;

namespace AgenceDAO {


    public abstract class DAOBase : IDisposable {

        protected IDBWrapper _db;
        //TODO : faire un fichier de configuration + une fabrique

        //fichier de configuration d'application (tous les fichiers portant une extension .config sont pris en compte) : 
        //http://msdn.microsoft.com/en-us/library/ms229689.aspx
        //on ne fait pas de fichier .config dans les packages, mais seulement dans l'application.
        //http://stackoverflow.com/questions/4817051/can-a-class-library-have-an-app-config-file

        public DAOBase() {
            _db = new SQLiteWrapper(VariablesGlobales.connectionString);
        }

        public abstract int Ajouter(IDBWrapper db, IAgenceDTO dto); //retourne l'identifiant créé si besoin
        public abstract void Modifier(IDBWrapper db, IAgenceDTO dto);       
        public abstract void Supprimer(IDBWrapper db, int idToDelete);

        public void Dispose() {
            _db.Dispose();
            GC.SuppressFinalize(this);
        }
    }
}