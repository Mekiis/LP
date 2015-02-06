using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier {

    public class ProspectMetier {

        public static void VerifierSaisie(ProspectDTO prospect) {
            PersonneMetier.VerifierSaisie(prospect);
            if (prospect.DateContact == null)
                throw new ExceptionMetier("Vous devez saisir la date de contact avec le prospect.");
        }


        public static void Ajouter(ProspectDTO prospect, UniteMetier um) {
            VerifierSaisie(prospect);
            ProspectDAO prospectDAO = new ProspectDAO();
            um.AjouterInsertion(prospectDAO, prospect);
        }


        public static List<ProspectDTO> ChargerListeProspects() {
            using (ProspectDAO prospectDAO = new ProspectDAO()) {
                return prospectDAO.ChargerListeProspects();
            }
        }

        public static ProspectDTO Charger(int idProspect) {
            using (ProspectDAO prospectDAO = new ProspectDAO()) {
                ProspectDTO prospect = prospectDAO.Charger(idProspect);
                if (prospect == null)
                    throw new ExceptionMetier("Le prospect n'existe pas dans la base de données.");
                return prospect;
            }
        }

        public static void Supprimer(int idProspect, UniteMetier um) {
            // TODO: vérifier si le Prospect à supprimer existe
            ProspectDAO prospectDAO = new ProspectDAO();
            um.AjouterSuppression(prospectDAO, idProspect);
        }

    }
}
