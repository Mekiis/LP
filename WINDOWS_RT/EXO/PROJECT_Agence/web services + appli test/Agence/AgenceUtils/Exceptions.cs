using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgenceUtils {


    public class ExceptionMetier : Exception {

        public ExceptionMetier(String message) : base(message) { }

    }

    public class ExceptionDAO : Exception {

        private String _sql;

        public ExceptionDAO(String Message)
            : base(Message) {
        }

        public ExceptionDAO(String Message, String SQL, Exception inner)
            : base(String.Format("{0}\r\nSQL : {1}", Message, SQL), inner) {
            _sql = SQL;
        }



    }
}
