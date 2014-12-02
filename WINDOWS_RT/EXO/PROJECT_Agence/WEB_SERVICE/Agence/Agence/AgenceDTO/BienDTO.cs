using System;

namespace AgenceDTO
{

    //Martin Fowler, à propos des DTO : voir "When to use it", P366 du document "Patterns of Enterprise Application Architecture"

    public class TypeBienDTO : IAgenceDTO  {

        private String _libelleType = String.Empty;
        public String LibelleType {
            get { return _libelleType; }
            set { _libelleType = value; }
        }

        private int _idTypeBien = -1;
        public int IdTypeBien {
            get { return _idTypeBien; }
            set { _idTypeBien = value; }
        }
    }


    public class BienDTO : TypeBienDTO {

        private int _idBien = -1;
        public int IdBien {
            get { return _idBien; }
            set { _idBien = value; }
        }

        private String _adresse = String.Empty;
        public String Adresse {
            get { return _adresse; }
            set { _adresse = value; }
        }

        private String _latitude = String.Empty;
        public String Latitude {
            get { return _latitude; }
            set { _latitude = value; }
        }

        private String _longitude = String.Empty;
        public String Longitude {
            get { return _longitude; }
            set { _longitude = value; }
        }

        private String _description = String.Empty;
        public String Description {
            get { return _description; }
            set { _description = value; }
        }

        private ProprietaireDTO _proprietaire = null;
        public ProprietaireDTO Proprietaire {
            get { return _proprietaire; }
            set { _proprietaire = value; }
        }

        private String _titre = String.Empty;
        public String Titre {
            get { return _titre; }
            set { _titre = value; }
        }

    }
}
