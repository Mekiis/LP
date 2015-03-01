using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class AnnonceEntite : EntiteBase {

        private int _idAnnonce = -1;
        public int IdAnnonce {
            get { return _idAnnonce; }
            set {
                if (_idAnnonce != value) {
                    _idAnnonce = value;
                    RaisePropertyChanged(() => IdAnnonce);
                }
            }
        }

        private float _prix = -1;
        public float Prix {
            get { return _prix; }
            set {
                if (_prix != value) {
                    _prix = value;
                    RaisePropertyChanged(() => Prix);
                }
            }
        }

        private string _texte = String.Empty;
        public string Texte {
            get { return _texte; }
            set {
                if (_texte != value) {
                    _texte = value;
                    RaisePropertyChanged(() => Texte);
                }
            }
        }

        private string _titre = String.Empty;
        public string Titre {
            get { return _titre; }
            set {
                if (_titre != value) {
                    _titre = value;
                    RaisePropertyChanged(() => Titre);
                }
            }
        }

        private BienEntite _bien = null;
        public BienEntite Bien {
            get { return _bien; }
            set {
                _bien = value;
                RaisePropertyChanged(() => Bien);
            }
        }
    }
}
