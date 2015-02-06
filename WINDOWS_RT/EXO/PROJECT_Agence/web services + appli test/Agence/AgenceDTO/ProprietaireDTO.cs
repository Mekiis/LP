using System;

namespace AgenceDTO {

    public class ProprietaireDTO : PersonneDTO {

        private String _adresse = String.Empty;
        public String Adresse {
            get { return _adresse; }
            set { _adresse = value; }
        }

    }

}
