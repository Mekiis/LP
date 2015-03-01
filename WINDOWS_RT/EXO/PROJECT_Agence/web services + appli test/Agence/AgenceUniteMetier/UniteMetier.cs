using System.Collections.Generic;
using AgenceDTO;
using AgenceDAO;
using AgenceUtils;
using System.Transactions;
using System;

namespace AgenceUniteMetier
{
    
    //pattern UnitOfWork, par Martin Fowler
    //http://takacsot.freeblog.hu/Files/martinfowler/unitOfWork.html
    //L'unité métier est créée par la couche service.
    //Dès qu'un métier doit créér / modifier / supprimer une donnée dans la base, il doit utiliser les méthode AjouterInsertion, AjouterModification, AjouterSuppression
    //Les enregistrements de la base sont donc vérouillés très peu de temps.

    public class UniteMetier
    {

        //conteneurs
        internal struct UMStructInsertOrUpdate {
            internal DAOBase DAO;
            internal IAgenceDTO Donnees;
        }

        internal struct UMStructDelete {
            internal DAOBase DAO;
            internal int ID;
        }

        private List<UMStructInsertOrUpdate> _donneesAInserer;
        private List<UMStructInsertOrUpdate> _donneesAModifier;
        private List<UMStructDelete> _donneesASupprimer;


        public UniteMetier() {
            _donneesAInserer = new List<UMStructInsertOrUpdate>();
            _donneesAModifier = new List<UMStructInsertOrUpdate>();
            _donneesASupprimer = new List<UMStructDelete>();
        }


        public void AjouterInsertion(DAOBase dao, IAgenceDTO dto) {
            UMStructInsertOrUpdate structure;
            structure.DAO = dao;
            structure.Donnees = dto;
            _donneesAInserer.Add(structure);
        }


        public void AjouterModification(DAOBase dao, IAgenceDTO dto) {
            UMStructInsertOrUpdate structure;
            structure.DAO = dao;
            structure.Donnees = dto;
            _donneesAModifier.Add(structure);
        }


        public void AjouterSuppression(DAOBase dao, int idToDelete) {
            UMStructDelete structure;
            structure.DAO = dao;
            structure.ID = idToDelete;
            _donneesASupprimer.Add(structure);
        }


        public void Executer() {

            //"Introducing System.Transactions" : http://msdn.microsoft.com/en-us/library/ms973865.aspx
            using (TransactionScope tr = new TransactionScope()) {

                using (IDBWrapper db = new SQLiteWrapper(VariablesGlobales.connectionString)) {

                    //exécution des insertions...
                    foreach (UMStructInsertOrUpdate insertions in _donneesAInserer)
                        insertions.DAO.Ajouter(db, insertions.Donnees);
                    _donneesAInserer.Clear();

                    //puis des suppressions...
                    foreach (UMStructDelete suppressions in _donneesASupprimer)
                        suppressions.DAO.Supprimer(db, suppressions.ID);
                    _donneesASupprimer.Clear();

                    //et enfin des mises à jour
                    foreach (UMStructInsertOrUpdate modifications in _donneesAModifier)
                        modifications.DAO.Modifier(db, modifications.Donnees);
                    _donneesAModifier.Clear();

                    tr.Complete();
                }
            }
        }
    }
}
