using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using AgenceRT.AgenceWebService;

// Pour en savoir plus sur le modèle d'élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkId=234238

namespace AgenceRT
{
    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
        }

        /// <summary>
        /// Invoqué lorsque cette page est sur le point d'être affichée dans un frame.
        /// </summary>
        /// <param name="e">Données d'événement décrivant la manière dont l'utilisateur a accédé à cette page. La propriété Parameter
        /// est généralement utilisée pour configurer la page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
        }

        private void txtBiens_Tapped(object sender, TappedRoutedEventArgs e) {
            Frame.Navigate(typeof(ListeBiens));
        }

        private void applicationTitle_Tapped(object sender, TappedRoutedEventArgs e) {
            Frame.Navigate(typeof(TestPage));
        }

        private async void Button_Click_1(object sender, RoutedEventArgs e) {
            AgenceWebServicesClient ws = new AgenceWebServicesClient();
            ObservableCollection<TypeBienDTO> list =  await ws.ChargerListeTypesBiensAsync();

            //AgendaDTO ag = new AgendaDTO();
            //ag.Agent = new AgentDTO { IdPersonne = 1 };
            //ag.Annonce = new AnnonceDTO { IdAnnonce = 2 };
            //ag.Date = DateTime.Now;
            //ag.Titre = "RDV très important";
            //ag.Prospect = new ProspectDTO { IdPersonne = 2 };
            //await ws.AjouterAgendaAsync(ag);

           // ProprietaireDTO p = new ProprietaireDTO { Nom = "Patrick", Prenom = "Fiori" , Adresse = "Paris" };
           //await ws.AjouterProprietaireAsync(p);

            ws.SupprimerProprietaireAsync("12");

        }
    }
}
