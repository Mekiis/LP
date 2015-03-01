using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class ProprietaireMetier {

        public static void VerifierSaisie(ProprietaireDTO proprietaire) {
            PersonneMetier.VerifierSaisie(proprietaire);
            if (proprietaire.Adresse == String.Empty)
                throw new ExceptionMetier("Vous devez saisir l'adresse du propriétaire.");
        }


        public static void Ajouter(ProprietaireDTO proprietaire, UniteMetier um) {
            VerifierSaisie(proprietaire);
            ProprietaireDAO proprietaireDAO = new ProprietaireDAO();
            um.AjouterInsertion(proprietaireDAO, proprietaire);
        }


        public static List<ProprietaireDTO> ChargerListeProprietaires() {
            using (ProprietaireDAO proprietaireDAO = new ProprietaireDAO()) {
                return proprietaireDAO.ChargerListeProprietaires();
            }
        }

        public static ProprietaireDTO Charger(int idProprietaire) {
            using (ProprietaireDAO proprietaireDAO = new ProprietaireDAO()) {
                ProprietaireDTO proprietaire = proprietaireDAO.Charger(idProprietaire);
                if (proprietaire == null)
                    throw new ExceptionMetier("Le proprietaire n'existe pas dans la base de données.");
                return proprietaire;
            }
        }

        public static void Supprimer(int idProprietaire, UniteMetier um) {
            // TODO: vérifier si le Proprietaire à supprimer existe
            ProprietaireDAO proprietaireDAO = new ProprietaireDAO();
            um.AjouterSuppression(proprietaireDAO, idProprietaire);
        }

    }
}
