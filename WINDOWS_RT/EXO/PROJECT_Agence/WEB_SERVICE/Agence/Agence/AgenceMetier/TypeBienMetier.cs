using System;
using AgenceDTO;
using AgenceDAO;
using System.Collections.Generic;

namespace AgenceMetier {

    public class TypeBienMetier {

        public static List<TypeBienDTO> ChargerListeTypesBiens() {
            using (TypeBienDAO typeBien = new TypeBienDAO()) {
                return typeBien.ChargerListeTypesBiens();
            }
        }
    }
}
