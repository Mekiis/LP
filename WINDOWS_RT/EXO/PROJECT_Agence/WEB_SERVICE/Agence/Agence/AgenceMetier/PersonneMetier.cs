using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class PersonneMetier {

        public static void VerifierSaisie(PersonneDTO personne) {
            if (personne.Nom == String.Empty)
                throw new ExceptionMetier("Vous devez saisir le nom de la personne.");

            else if (personne.Prenom == String.Empty)
                throw new ExceptionMetier("Vous devez saisir le prénom de la personne.");
        }


        public static void Ajouter(PersonneDTO personne, UniteMetier um) {
            VerifierSaisie(personne);
            PersonneDAO personneDAO = new PersonneDAO();
            um.AjouterInsertion(personneDAO, personne);
        }


        public static List<PersonneDTO> ChargerListePersonnes() {
            using (PersonneDAO personneDAO = new PersonneDAO()) {
                return personneDAO.ChargerListePersonnes();
            }
        }

        public static PersonneDTO Charger(int idPersonne) {
            using (PersonneDAO personneDAO = new PersonneDAO()) {
                PersonneDTO personne = personneDAO.Charger(idPersonne);
                if (personne == null)
                    throw new ExceptionMetier("Le personne n'existe pas dans la base de données.");
                return personne;
            }
        }

        public static void Supprimer(int idPersonne, UniteMetier um) {
            // TODO: vérifier si le Personne à supprimer existe
            PersonneDAO personneDAO = new PersonneDAO();
            um.AjouterSuppression(personneDAO, idPersonne);
        }

    }
}
