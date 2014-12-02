using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceDTO {

    public class AgendaDTO : IAgenceDTO {

        private int _idAgenda;
        public int IdAgenda {
            get { return _idAgenda; }
            set { _idAgenda = value; }
        }

        private DateTime? _date;
        public DateTime? Date {
            get { return _date; }
            set { _date = value; }
        }

        private String _description = String.Empty;
        public String Description {
            get { return _description; }
            set { _description = value; }
        }

        private String _titre = String.Empty;
        public String Titre {
            get { return _titre; }
            set { _titre = value; }
        }

        private AgentDTO _agent = null;
        public AgentDTO Agent {
            get { return _agent; }
            set { _agent = value; }
        }

        private ProspectDTO _prospect = null;
        public ProspectDTO Prospect {
            get { return _prospect; }
            set { _prospect = value; }
        }

        private AnnonceDTO _annonce = null;
        public AnnonceDTO Annonce {
            get { return _annonce; }
            set { _annonce = value; }
        }

    }
}
