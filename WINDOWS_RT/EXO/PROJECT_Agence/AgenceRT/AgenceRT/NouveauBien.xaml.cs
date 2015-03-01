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
using Windows.UI.Popups;
using AgenceRT.ViewModels;
using AgenceRT.Common;

// Pour en savoir plus sur le modèle d'élément Page de base, consultez la page http://go.microsoft.com/fwlink/?LinkId=234237

namespace AgenceRT {
    /// <summary>
    /// Page de base qui inclut des caractéristiques communes à la plupart des applications.
    /// </summary>
    public sealed partial class NouveauBien : AgenceRT.Common.LayoutAwarePage {

        private NouveauBienViewModel _nouveauBienViewModel;

        public NouveauBienViewModel NouveauBienViewModel {
            get { return _nouveauBienViewModel; }
            set { _nouveauBienViewModel = value; }
        }

        public NouveauBien() {           
            this.InitializeComponent();
            _nouveauBienViewModel = new NouveauBienViewModel((s) => { MessageDialog msg = new MessageDialog(Utils.FormaterMessageWS(s)); msg.ShowAsync(); }, Naviguer);
            this.DataContext = NouveauBienViewModel;
        }

        public void Naviguer(String destination) {
            if (destination == "retour") Frame.GoBack();
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
        }

        /// <summary>
        /// Conserve l'état associé à cette page en cas de suspension de l'application ou de la
        /// suppression de la page du cache de navigation. Les valeurs doivent être conformes aux
        /// exigences en matière de sérialisation de <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="pageState">Dictionnaire vide à remplir à l'aide de l'état sérialisable.</param>
        protected override void SaveState(Dictionary<String, Object> pageState) {
        }

    }
}
