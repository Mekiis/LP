using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
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

// Pour en savoir plus sur le modèle d'élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkId=234238

namespace AgenceRT {

    public class BienObservable : INotifyPropertyChanged {

        private String _titre;
        public event PropertyChangedEventHandler PropertyChanged;

        public String Titre {
            get { return _titre; }
            set {
                if (_titre != value) {
                    _titre = value;
                    RaisePropertyChanged("Titre");
                }
            }
        }

        protected void RaisePropertyChanged(String propriete) {
            if (propriete != String.Empty) {
                PropertyChangedEventHandler handler = PropertyChanged;

                if (handler != null)
                    PropertyChanged(this, new PropertyChangedEventArgs(propriete));
            }
        }

    }

    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    /// 

    public sealed partial class TestPage : Page {

        private ObservableCollection<BienObservable> _biens = new ObservableCollection<BienObservable>();

        public ObservableCollection<BienObservable> Biens {
            get { return _biens; }
            set { _biens = value; }
        }

        public TestPage() {
            this.DataContext = this;
            this.InitializeComponent();

            _biens.Add(new BienObservable { Titre = "Bien numéro 1" });    //Initialisateurs de collection : http://msdn.microsoft.com/fr-fr/library/bb384062.aspx
            _biens.Add(new BienObservable { Titre = "Bien numéro 2" });
            _biens.Add(new BienObservable { Titre = "Bien numéro 3" });
           
        }

        /// <summary>
        /// Invoqué lorsque cette page est sur le point d'être affichée dans un frame.
        /// </summary>
        /// <param name="e">Données d'événement décrivant la manière dont l'utilisateur a accédé à cette page. La propriété Parameter
        /// est généralement utilisée pour configurer la page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e) {
        }
    }
}
