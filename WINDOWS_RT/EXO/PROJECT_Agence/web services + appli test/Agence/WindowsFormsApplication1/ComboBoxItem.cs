using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1 {

    class ComboBoxItem {
        public String Valeur;
        public int ID;

        public ComboBoxItem(int id, string valeur) {
            Valeur = valeur;
            ID = id;
        }

        //Override ToString method
        public override string ToString() {
            return Valeur;
        }

    }
}
