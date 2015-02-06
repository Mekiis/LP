using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceDTO {
    public class ProspectDTO : PersonneDTO {

        private DateTime? _dateContact = null;
        public DateTime? DateContact {
            get { return _dateContact; }
            set { _dateContact = value; }
        }
    }
}
