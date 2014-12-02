using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using AgenceService;
using AgenceDTO;
using AgenceUtils;

namespace AgenceWebServices {
    // REMARQUE : vous pouvez utiliser la commande Renommer du menu Refactoriser pour changer le nom de classe "Service1" dans le code, le fichier svc et le fichier de configuration.
    // REMARQUE : pour lancer le client test WCF afin de tester ce service, sélectionnez Service1.svc ou Service1.svc.cs dans l'Explorateur de solutions et démarrez le débogage.
    public class AgenceWebServices : IAgenceWebServices {

        private void GererException(Exception e) {
            if (e.GetType() == typeof(AgenceUtils.ExceptionMetier)) {
                WebOperationContext.Current.OutgoingResponse.StatusCode = System.Net.HttpStatusCode.BadRequest;
                WebOperationContext.Current.OutgoingResponse.StatusDescription = e.Message;
            }
            else {
                //normalement, mettre HTTP 500 en cas d'erreur interne du serveur. Malheureusement, le proxy WP7 ne provoque pas d'exception dans ce cas...
                ///WebOperationContext.Current.OutgoingResponse.StatusCode = System.Net.HttpStatusCode.InternalServerError;
                WebOperationContext.Current.OutgoingResponse.StatusCode = System.Net.HttpStatusCode.BadRequest;
                WebOperationContext.Current.OutgoingResponse.StatusDescription = "Une erreur interne au serveur s'est produite.";
            }
            Utils.LogException(e);
        }


        #region biens
        /////////////////////////////////////////
        // Biens
        /////////////////////////////////////////

        public BienDTO ChargerBien(string id) {
            try {
                int idBien = Convert.ToInt16(id);
                return BienService.Charger(idBien);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<BienDTO> ChargerListeBiens() {
            return BienService.ChargerListeBiens();
        }

        public List<BienDTO> ChargerListeBiensParType(string idType) {
            int idTypeBien = Convert.ToInt16(idType);
            return BienService.ChargerListeBiens(idTypeBien);
        }

        public void AjouterBien(BienDTO bien) {
            try {
                BienService.Ajouter(bien);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public List<TypeBienDTO> ChargerListeTypesBiens() {
            return TypeBienService.ChargerListesTypesBiens();
        }

        public void SupprimerBien(string id) {
            try {
                int idBien = Convert.ToInt16(id);
                BienService.Supprimer(idBien);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        #region prospects
        /////////////////////////////////////////
        // Prospects
        /////////////////////////////////////////

        public ProspectDTO ChargerProspect(string id) {
            try {
                int idProspect = Convert.ToInt16(id);
                return ProspectService.Charger(idProspect);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<ProspectDTO> ChargerListeProspects() {
            return ProspectService.ChargerListeProspects();
        }

        public void AjouterProspect(ProspectDTO prospect) {
            try {
                ProspectService.Ajouter(prospect);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public void SupprimerProspect(string id) {
            try {
                int idProspect = Convert.ToInt16(id);
                ProspectService.Supprimer(idProspect);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        #region agents
        /////////////////////////////////////////
        // Agents
        /////////////////////////////////////////

        public AgentDTO ChargerAgent(string id) {
            try {
                int idAgent = Convert.ToInt16(id);
                return AgentService.Charger(idAgent);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<AgentDTO> ChargerListeAgents() {
            return AgentService.ChargerListeAgents();
        }

        public void AjouterAgent(AgentDTO agent) {
            try {
                AgentService.Ajouter(agent);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public void SupprimerAgent(string id) {
            try {
                int idAgent = Convert.ToInt16(id);
                AgentService.Supprimer(idAgent);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        #region proprietaires
        /////////////////////////////////////////
        // Proprietaires
        /////////////////////////////////////////

        public ProprietaireDTO ChargerProprietaire(string id) {
            try {
                int idProprietaire = Convert.ToInt16(id);
                return ProprietaireService.Charger(idProprietaire);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<ProprietaireDTO> ChargerListeProprietaires() {
            return ProprietaireService.ChargerListeProprietaires();
        }

        public void AjouterProprietaire(ProprietaireDTO proprietaire) {
            try {
                ProprietaireService.Ajouter(proprietaire);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public void SupprimerProprietaire(string id) {
            try {
                int idProprietaire = Convert.ToInt16(id);
                ProprietaireService.Supprimer(idProprietaire);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        #region agenda
        /////////////////////////////////////////
        // Agenda
        /////////////////////////////////////////

        public AgendaDTO ChargerAgenda(string id) {
            try {
                int idAgenda = Convert.ToInt16(id);
                return AgendaService.Charger(idAgenda);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<AgendaDTO> ChargerListeRendezVous() {
            return AgendaService.ChargerListeRendezVous();
        }

        public void AjouterAgenda(AgendaDTO agenda) {
            try {
                AgendaService.Ajouter(agenda);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public void SupprimerAgenda(string id) {
            try {
                int idAgenda = Convert.ToInt16(id);
                AgendaService.Supprimer(idAgenda);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        #region annonces
        /////////////////////////////////////////
        // Annonces
        /////////////////////////////////////////

        public AnnonceDTO ChargerAnnonce(string id) {
            try {
                int idAnnonce = Convert.ToInt16(id);
                return AnnonceService.Charger(idAnnonce);
            }
            catch (Exception e) {
                GererException(e);
                return null;
            }
        }

        public List<AnnonceDTO> ChargerListeAnnonces() {
            return AnnonceService.ChargerListeAnnonces();
        }

        public void AjouterAnnonce(AnnonceDTO annonce) {
            try {
                AnnonceService.Ajouter(annonce);
            }
            catch (Exception e) {
                GererException(e);
            }
        }

        public void SupprimerAnnonce(string id) {
            try {
                int idAnnonce = Convert.ToInt16(id);
                AnnonceService.Supprimer(idAnnonce);
            }
            catch (Exception e) {
                GererException(e);
            }
        }
        #endregion


        public void EnregistrerSaisie(IAgenceDTO saisie) {
            try {
                Type typeSaisie = saisie.GetType();
                if (typeSaisie == typeof(AgenceDTO.BienDTO)) {
                    BienDTO bien = (BienDTO)saisie;
                    BienService.Ajouter(bien); 
                }
                else if (typeSaisie == typeof(AgenceDTO.AgendaDTO)) {
                    AgendaDTO agenda = (AgendaDTO)saisie;
                    AgendaService.Ajouter(agenda);
                }
                else if (typeSaisie == typeof(AgenceDTO.AnnonceDTO)) {
                    AnnonceDTO annonce = (AnnonceDTO)saisie;
                    AnnonceService.Ajouter(annonce);
                }
                else if (typeSaisie == typeof(AgenceDTO.ProprietaireDTO)) {
                    ProprietaireDTO proprietaire = (ProprietaireDTO)saisie;
                    ProprietaireService.Ajouter(proprietaire);
                }
                else if (typeSaisie == typeof(AgenceDTO.ProspectDTO)) {
                    ProspectDTO prospect = (ProspectDTO)saisie;
                    ProspectService.Ajouter(prospect);
                }
                else if (typeSaisie == typeof(AgenceDTO.AgentDTO)) {
                    AgentDTO agent = (AgentDTO)saisie;
                    AgentService.Ajouter(agent);
                }
            }
            catch (Exception e) {
                GererException(e);
            }

        }

    }
}
