using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites {

    public class ProspectEntite : EntiteBase {

        private DateTime? _dateContact = null;
        public DateTime? DateContact {
            get { return _dateContact; }
            set {
                if (_dateContact != value) {
                    _dateContact = value;
                    RaisePropertyChanged(() => DateContact);
                }
            }
        }
    }


}
