using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class PersonneEntite : EntiteBase {

        private int _idPersonne = -1;
        public int IdPersonne {
            get { return _idPersonne; }
            set {
                if (_idPersonne != value) {
                    _idPersonne = value;
                    RaisePropertyChanged(() => IdPersonne);
                }
            }
        }


        private String _nom = String.Empty;
        public String Nom {
            get { return _nom; }
            set {
                if (_nom != value) {
                    _nom = value;
                    RaisePropertyChanged(() => Nom);
                }
            }
        }

        private String _prenom = String.Empty;
        public String Prenom {
            get { return _prenom; }
            set {
                if (_prenom != value) {
                    _prenom = value;
                    RaisePropertyChanged(() => Prenom);
                }
            }
        }

        private String _telephone = String.Empty;
        public String Telephone {
            get { return _telephone; }
            set {
                if (_telephone != value) {
                    _telephone = value;
                    RaisePropertyChanged(() => Telephone);
                }
            }
        }
    }
}
