using System;
using System.Collections.Generic;
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
using System.Collections.ObjectModel;
using AgenceEntites;
using Windows.UI.Popups;
using Windows.ApplicationModel.Search;

// Pour en savoir plus sur le modèle d'élément Page de base, consultez la page http://go.microsoft.com/fwlink/?LinkId=234237

namespace AgenceRT {
    /// <summary>
    /// Page de base qui inclut des caractéristiques communes à la plupart des applications.
    /// </summary>
    public sealed partial class ListeBiens : Common.LayoutAwarePage {

        private ObservableCollection<BienEntite> _biensReference = new ObservableCollection<BienEntite>();
        private ObservableCollection<BienEntite> _biensAffiches = new ObservableCollection<BienEntite>();

        public ObservableCollection<BienEntite> BiensAffiches {
            get { return _biensAffiches; }
            set { _biensAffiches = value; }
        }

        private SearchPane _panelRecherche;

        public ListeBiens() {

            this.InitializeComponent();

            this.DataContext = this;

            this._panelRecherche = SearchPane.GetForCurrentView();
            this._panelRecherche.QuerySubmitted += _panelRecherche_QuerySubmitted;
        }

        void _panelRecherche_QuerySubmitted(SearchPane sender, SearchPaneQuerySubmittedEventArgs args) {
            if (args.QueryText != String.Empty) {
                var resultats = from bien in _biensReference
                                where bien.Titre.Contains(args.QueryText)
                                select bien;

                _biensAffiches.Clear();
                foreach (BienEntite bien in resultats)
                    _biensAffiches.Add(bien);
            }
            else {
                _biensAffiches.Clear();
                foreach (BienEntite bien in _biensReference)
                    _biensAffiches.Add(bien);
            }
        }

        /// <summary>
        /// Remplit la page à l'aide du contenu passé lors de la navigation. Tout état enregistré est également
        /// fourni lorsqu'une page est recréée à partir d'une session antérieure.
        /// </summary>
        /// <param name="navigationParameter">Valeur de paramètre passée à
        /// <see cref="Frame.Navigate(Type, Object)"/> lors de la requête initiale de cette page.
        /// </param>
        /// <param name="pageState">Dictionnaire d'état conservé par cette page durant une session
        /// antérieure. Null lors de la première visite de la page.</param>
        protected override void LoadState(Object navigationParameter, Dictionary<String, Object> pageState) {
            AppBar1.IsOpen = true;
        }

        /// <summary>
        /// Conserve l'état associé à cette page en cas de suspension de l'application ou de la
        /// suppression de la page du cache de navigation. Les valeurs doivent être conformes aux
        /// exigences en matière de sérialisation de <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="pageState">Dictionnaire vide à remplir à l'aide de l'état sérialisable.</param>
        protected override void SaveState(Dictionary<String, Object> pageState) {
        }

        protected override void OnNavigatedTo(NavigationEventArgs e) {
            ChargerBiens();
        }

        private async  void btnSupprimer_Tapped(object sender, TappedRoutedEventArgs e) {
            MessageDialog messageDialog = new MessageDialog("Voulez-vous supprimer ce bien ?");

            // ajout des commandes et de leurs callbacks
            messageDialog.Commands.Add(new UICommand("Oui", OnYesButtonClicked));
            messageDialog.Commands.Add(new UICommand("Non"));

            // commande sélectionnée par défaut : le bouton "Non"
            messageDialog.DefaultCommandIndex = 1;

            // quand l'utilisateur appuise sur "echap", le bouton "Non" sera exécuté
            messageDialog.CancelCommandIndex = 1;

            await messageDialog.ShowAsync();

            AppBar1.IsOpen = true;
        }

        private async void OnYesButtonClicked(IUICommand command) {
            AgenceWebServicesClient ws = new AgenceWebServicesClient();
            await ws.SupprimerBienAsync(((BienEntite)listeBiens.SelectedItem).IdBien.ToString());
            ChargerBiens();
        }

        private void btnValider(object sender, TappedRoutedEventArgs e) {
            Frame.Navigate(typeof(NouveauBien));
        }

        private async void ChargerBiens() {
            _biensReference.Clear();
            _biensAffiches.Clear();
            ObservableCollection<BienDTO> biens;
            AgenceWebServicesClient ws = new AgenceWebServicesClient();

            biens = await ws.ChargerListeBiensAsync();

            foreach (BienDTO bien in biens) {
                BienEntite bienEntite = new BienEntite { Titre = bien.Titre, Description = bien.Description, IdBien = bien.IdBien };
                _biensReference.Add(bienEntite);
                _biensAffiches.Add(bienEntite);
            }
        }
    }
}
