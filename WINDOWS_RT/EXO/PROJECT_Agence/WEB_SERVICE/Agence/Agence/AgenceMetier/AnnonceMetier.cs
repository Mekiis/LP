using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class AnnonceMetier {

        public static void VerifierSaisie(AnnonceDTO annonce) {
            if (annonce.Bien == null)
                throw new ExceptionMetier("Vous devez choisir un bien à vendre.");
            else if (annonce.Titre == string.Empty)
                throw new ExceptionMetier("Vous devez saisir le titre de l'annonce.");
            else if (annonce.Prix == -1)
                throw new ExceptionMetier("Vous devez saisir le prix du bien vendu.");
        }


        public static void Ajouter(AnnonceDTO annonce, UniteMetier um) {
            VerifierSaisie(annonce);
            AnnonceDAO annonceDAO = new AnnonceDAO();
            um.AjouterInsertion(annonceDAO, annonce);
        }


        public static List<AnnonceDTO> ChargerListeAnnonces() {
            using (AnnonceDAO annonceDAO = new AnnonceDAO()) {
                return annonceDAO.ChargerListeAnnonces();
            }
        }

        public static AnnonceDTO Charger(int idAnnonce) {
            using (AnnonceDAO annonceDAO = new AnnonceDAO()) {
                AnnonceDTO annonce = annonceDAO.Charger(idAnnonce);
                if (annonce == null)
                    throw new ExceptionMetier("L'annonce n'existe pas dans la base de données.");
                return annonce;
            }
        }

        public static void Supprimer(int idAnnonce, UniteMetier um) {
            using (AnnonceDAO annonceDAO = new AnnonceDAO()) {
                AnnonceDTO annonce = annonceDAO.Charger(idAnnonce);
                if (annonce == null)
                    throw new ExceptionMetier("L'annonce n'existe pas dans la base de données.");
                um.AjouterSuppression(annonceDAO, idAnnonce);
            }
            
        }

    }
}
