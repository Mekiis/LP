using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class ProprietaireEntite : EntiteBase {
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
    }
}
