using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceDTO {

    public class AgentDTO : PersonneDTO {

        private string _login = String.Empty;
        public string Login {
            get { return _login; }
            set { _login = value; }
        }

        private string _motDePasse = String.Empty;
        public string MotDePasse {
            get { return _motDePasse; }
            set { _motDePasse = value; }
        }

    }
}
