using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceRT.Common {

    public class Utils {

        public static string FormaterMessageWS(string s) {
            return s.Split(')')[1].Replace("..", ".");
        }
    }
}
