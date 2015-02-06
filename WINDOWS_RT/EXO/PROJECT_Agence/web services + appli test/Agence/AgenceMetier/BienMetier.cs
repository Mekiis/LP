using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;
using AgenceUniteMetier;
using AgenceUtils;

namespace AgenceMetier
{
    public class BienMetier {


        public static void VerifierSaisie(BienDTO bien) {
            if (bien.Titre == String.Empty)
                throw new ExceptionMetier("Vous devez saisir le titre du Bien.");

            else if ((bien.IdTypeBien == -1) || (bien.IdTypeBien == 0))
                throw new ExceptionMetier("Vous devez saisir le type de Bien.");
        }


        public static void Ajouter(BienDTO bien, UniteMetier um) {
            VerifierSaisie(bien);
            BienDAO bienDAO = new BienDAO();
            um.AjouterInsertion(bienDAO, bien);
        }


        public static List<BienDTO> ChargerListeBiens(int idTypeBien = -1) {
            using (BienDAO bienDAO = new BienDAO()) {
                return bienDAO.ChargerListeBiens(idTypeBien);
            }
        }

        public static BienDTO Charger(int idBien) {
            using (BienDAO bienDAO = new BienDAO()) {
                BienDTO bien = bienDAO.Charger(idBien);
                if (bien == null)
                    throw new ExceptionMetier("Le bien n'existe pas dans la base de données.");
                return bien;
            }
        }

        public static void Supprimer(int idBien, UniteMetier um) {
            using (AgendaDAO agendaDAO = new AgendaDAO()) {
                if (agendaDAO.VerifierSiBienDansAgenda(idBien))
                    throw new ExceptionMetier("Vous ne pouvez pas supprimer ce bien : il est utilisé dans l'agenda.");
            }
            using (AnnonceDAO annonceDAO = new AnnonceDAO()) {
                if (annonceDAO.VerifierSiBienDansAnnonce(idBien))
                    throw new ExceptionMetier("Vous ne pouvez pas supprimer ce bien : il est utilisé dans une ou plusieurs annonces de vente.");
            }
            using (BienDAO bienDAO = new BienDAO()) {
                BienDTO bien = bienDAO.Charger(idBien);
                if (bien == null)
                    throw new ExceptionMetier("Le bien à supprimer n'existe pas dans la base de données.");
                um.AjouterSuppression(bienDAO, idBien);
            }          
        }

    }
}
