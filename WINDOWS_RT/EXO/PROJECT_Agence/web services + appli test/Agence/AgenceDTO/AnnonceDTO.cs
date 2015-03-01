using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceDTO {
    public class AnnonceDTO : IAgenceDTO {

        private int _idAnnonce = -1;
        public int IdAnnonce {
            get { return _idAnnonce; }
            set { _idAnnonce = value; }
        }

        private float _prix = -1;
        public float Prix {
            get { return _prix; }
            set { _prix = value; }
        }

        private string _texte = String.Empty;
        public string Texte {
            get { return _texte; }
            set { _texte = value; }
        }

        private string _titre = String.Empty;
        public string Titre {
            get { return _titre; }
            set { _titre = value; }
        }

        private BienDTO _bien = null;
        public BienDTO Bien {
            get { return _bien; }
            set { _bien = value; }
        }
    }
}
