using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class TypeBienEntite : EntiteBase {

        private String _libelleType = String.Empty;
        public String LibelleType {
            get { return _libelleType; }
            set { 
                if (_libelleType != value) {
                    _libelleType = value; 
                    RaisePropertyChanged(() => LibelleType);
               }
            }
        }

        private int _idTypeBien = -1;
        public int IdTypeBien {
            get { return _idTypeBien; }
            set {
                if (_idTypeBien != value) {
                    _idTypeBien = value;
                    RaisePropertyChanged(() => IdTypeBien);
                }
            }
        }
    }


    public class BienEntite : TypeBienEntite {

        private int _idBien = -1;
        public int IdBien {
            get { return _idBien; }
            set { 
                if (_idBien != value) {
                    _idBien = value; 
                    RaisePropertyChanged(() => IdBien);
                }
            }
        }

        private String _adresse = String.Empty;
        public String Adresse {
            get { return _adresse; }
            set { 
                if (_adresse != value) {
                    _adresse = value; 
                    RaisePropertyChanged(() => Adresse);
                }
            }
        }

        private String _latitude = String.Empty;
        public String Latitude {
            get { return _latitude; }
            set { 
                if (_latitude != value) {
                    _latitude = value; 
                    RaisePropertyChanged(() => Latitude);
                }
            }
        }

        private String _longitude = String.Empty;
        public String Longitude {
            get { return _longitude; }
            set { 
                if (_longitude != value) {
                    _longitude = value; 
                    RaisePropertyChanged(() => Longitude);
                }
            }
        }

        private String _description = String.Empty;
        public String Description {
            get { return _description; }
            set { 
                if (_description != value) {
                    _description = value; 
                    RaisePropertyChanged(() => Description);
                }
            }
        }

        private ProprietaireEntite _proprietaire = null;
        public ProprietaireEntite Proprietaire {
            get { return _proprietaire; }
            set { 
                _proprietaire = value; 
                RaisePropertyChanged(() => Proprietaire);
            }
        }

        private String _titre = String.Empty;
        public String Titre {
            get { return _titre; }
            set { 
                if (_titre != value) {
                    _titre = value;
                    RaisePropertyChanged(() => Titre);
                }
            }
        }
    }
}
