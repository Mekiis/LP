using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AgenceEntites;
using AgenceRT.AgenceWebService;
using System.Windows.Input;
using System.ComponentModel;

namespace AgenceRT.ViewModels {

    public delegate void AfficherMessageHandler(String message);
    public delegate void NaviguerHandler(String to);

    public class NouveauBienViewModel {

        private BienEntite _bien = new BienEntite();
        public BienEntite Bien {
            get { return _bien; }
            set {  _bien = value; }
        }

        private ObservableCollection<TypeBienEntite> _typesBiens = new ObservableCollection<TypeBienEntite>();
        public ObservableCollection<TypeBienEntite> TypesBiens {
            get { return _typesBiens; }
            set { _typesBiens = value; }
        }

        private EnregistrerBienCommand _commandeEnregistrer = new EnregistrerBienCommand();
        private NaviguerHandler _navigateur;

        public EnregistrerBienCommand CommandeEnregistrer {
            get { return _commandeEnregistrer; }
            set { _commandeEnregistrer = value; }
        }

        public class EnregistrerBienCommand : ICommand {
            public event AfficherMessageHandler afficherMessage;
            public event NaviguerHandler navigateur;

            public bool CanExecute(object parameter) {
                //BienEntite bien = (BienEntite)parameter;
                //if (bien != null) {
                //    //CanExecuteChanged(this, new EventArgs());
                //    return bien.Titre != String.Empty && bien.IdTypeBien > 0;
                //}
                //else
                    return true;
            }

            public event EventHandler CanExecuteChanged;

            public async void Execute(object parameter) {
                AgenceWebServicesClient ws = new AgenceWebServicesClient();
                BienEntite bien = (BienEntite)parameter;
                try {
                    BienDTO bienDTO = new BienDTO { Titre = bien.Titre, Description = bien.Description, IdTypeBien = bien.IdTypeBien };
                    await ws.AjouterBienAsync(bienDTO);
                    if (navigateur != null) navigateur("retour");
                }

                catch (Exception e) {
                    if (afficherMessage != null) afficherMessage(e.Message);
                }
            }
        }

        public NouveauBienViewModel(AfficherMessageHandler afficherMessage, NaviguerHandler naviguer) {
            _commandeEnregistrer.afficherMessage += afficherMessage;
            _commandeEnregistrer.navigateur += naviguer;

            //initialisation des types de biens
            TypeBienEntite typeBien = new TypeBienEntite { IdTypeBien=1, LibelleType = "Garage"};
            _typesBiens.Add(typeBien);
            typeBien = new TypeBienEntite { IdTypeBien=2, LibelleType = "Appartement"};
            _typesBiens.Add(typeBien);
        }
    }
}
