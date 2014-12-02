using System;

namespace AgenceDTO {

    public class PersonneDTO : IAgenceDTO {

        private int _idPersonne = -1;
        public int IdPersonne {
            get { return _idPersonne; }
            set { _idPersonne = value; }
        }


        private String _nom = String.Empty;
        public String Nom {
            get { return _nom; }
            set { _nom = value; }
        }

        private String _prenom = String.Empty;
        public String Prenom {
            get { return _prenom; }
            set { _prenom = value; }
        }

        private String _telephone = String.Empty;
        public String Telephone {
            get { return _telephone; }
            set { _telephone = value; }
        }
       
    }

}
