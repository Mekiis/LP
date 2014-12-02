using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceDTO {
    public interface IAgenceDTO {
    }

    public abstract class AbstractSaisieDTO : IAgenceDTO {
        public Guid guid = new Guid();
    }
}
