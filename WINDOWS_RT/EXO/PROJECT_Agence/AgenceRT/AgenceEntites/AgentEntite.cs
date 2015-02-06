using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class AgentEntite : EntiteBase {

        private string _login = String.Empty;
        public string Login {
            get { return _login; }
            set {
                if (_login != value) {
                    _login = value;
                    RaisePropertyChanged(() => Login);
                }
            }
        }

        private string _motDePasse = String.Empty;
        public string MotDePasse {
            get { return _motDePasse; }
            set {
                if (_motDePasse != value) {
                    _motDePasse = value;
                    RaisePropertyChanged(() => MotDePasse);
                }
            }
        }
    }
}
