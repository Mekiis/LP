using System;
using System.Transactions;
using AgenceMetier;
using AgenceDTO;
using System.Collections.Generic;

namespace AgenceService {


    public class TypeBienService {

        public static List<TypeBienDTO> ChargerListesTypesBiens() {
            return TypeBienMetier.ChargerListeTypesBiens();
        }

    }
}
