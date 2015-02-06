using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class AgendaEntite : EntiteBase {

        private int _idAgenda;
        public int IdAgenda {
            get { return _idAgenda; }
            set {
                if (_idAgenda != value) {
                    _idAgenda = value; 
                    RaisePropertyChanged(() => IdAgenda);
                }
            }
        }

        private DateTime? _date;
        public DateTime? Date {
            get { return _date; }
            set {
                if (_date != value) {
                    _date = value;
                    RaisePropertyChanged(() => Date);
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

        private AgentEntite _agent = null;
        public AgentEntite Agent {
            get { return _agent; }
            set { 
                _agent = value;
                RaisePropertyChanged(() => Agent);
            }
        }

        private ProspectEntite _prospect = null;
        public ProspectEntite Prospect {
            get { return _prospect; }
            set { 
                _prospect = value;
                RaisePropertyChanged(() => Prospect);
            }
        }

        private AnnonceEntite _annonce = null;
        public AnnonceEntite Annonce {
            get { return _annonce; }
            set { 
                _annonce = value;
                RaisePropertyChanged(() => Annonce);
            }
        }
    }
}
