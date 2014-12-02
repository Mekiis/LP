using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using AgenceDTO;
using AgenceService;

namespace AgenceWebServices {
    // REMARQUE : vous pouvez utiliser la commande Renommer du menu Refactoriser pour changer le nom d'interface "IService1" à la fois dans le code et le fichier de configuration.
    [ServiceContract]
    public interface IAgenceWebServices {

        #region biens
        /////////////////////////////////////////
        // Biens
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "biens", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<BienDTO> ChargerListeBiens();

        [OperationContract]
        [WebGet(UriTemplate = "bien/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        BienDTO ChargerBien(string id);

        [OperationContract]
        [WebGet(UriTemplate = "biens?type={idType}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<BienDTO> ChargerListeBiensParType(string idType);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "bien", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterBien(BienDTO bien);

        [OperationContract]
        [WebGet(UriTemplate = "typesbiens", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<TypeBienDTO> ChargerListeTypesBiens();

        [OperationContract]
        void SupprimerBien(string id);
        #endregion


        #region prospects
        /////////////////////////////////////////
        // Prospects
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "prospects", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<ProspectDTO> ChargerListeProspects();

        [OperationContract]
        [WebGet(UriTemplate = "prospect/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        ProspectDTO ChargerProspect(string id);


        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "prospect", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterProspect(ProspectDTO prospect);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "prospect", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void SupprimerProspect(string id);
        #endregion


        #region agents
        /////////////////////////////////////////
        // Agents
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "agents", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<AgentDTO> ChargerListeAgents();

        [OperationContract]
        [WebGet(UriTemplate = "agent/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        AgentDTO ChargerAgent(string id);


        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "agent", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterAgent(AgentDTO agent);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "agent", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void SupprimerAgent(string id);
        #endregion


        #region proprietaires
        /////////////////////////////////////////
        // Proprietaires
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "proprietaires", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<ProprietaireDTO> ChargerListeProprietaires();

        [OperationContract]
        [WebGet(UriTemplate = "proprietaire/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        ProprietaireDTO ChargerProprietaire(string id);


        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "proprietaire", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterProprietaire(ProprietaireDTO proprietaire);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "proprietaire", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void SupprimerProprietaire(string id);
        #endregion


        #region agenda
        /////////////////////////////////////////
        // Agendas
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "rendezvous", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<AgendaDTO> ChargerListeRendezVous();

        [OperationContract]
        [WebGet(UriTemplate = "rendezvous/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        AgendaDTO ChargerAgenda(string id);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "agenda", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterAgenda(AgendaDTO agenda);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "agenda", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void SupprimerAgenda(string id);
        #endregion


        #region annonces
        /////////////////////////////////////////
        // Annonces
        /////////////////////////////////////////

        [OperationContract]
        [WebGet(UriTemplate = "annonces", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        List<AnnonceDTO> ChargerListeAnnonces();

        [OperationContract]
        [WebGet(UriTemplate = "annonce/{id}", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        //important : le paramètre de l'URI Template doit s'appeler de la même façon que le paramètre passé à la fonction ci-dessous.
        AnnonceDTO ChargerAnnonce(string id);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "annonce", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void AjouterAnnonce(AnnonceDTO annonce);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "annonce", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void SupprimerAnnonce(string id);
        #endregion

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "saisie", ResponseFormat = WebMessageFormat.Xml, RequestFormat = WebMessageFormat.Xml)]
        void EnregistrerSaisie(IAgenceDTO saisie);
    }
}
