using System;
using System.Text.RegularExpressions;
using System.Configuration;
using NLog;

namespace AgenceUtils
{
    public static class VariablesGlobales {

        public enum TypeConnection {
            SQLite,
            Firebird
        }

        public static String connectionString = "D:\\Oyosoft\\Enseignement\\IUT\\2014\\Projet\\Agence\\Base\\agence.db";
        public static TypeConnection BaseDeDonnees = TypeConnection.SQLite;
    }


    public static class Utils {

        public static Boolean IsNumeric(String str) {
            Regex reNum = new Regex(@"^\d+$");
            bool isNumeric = reNum.Match(str).Success;

            return isNumeric;
        }

        public static void LogException(Exception e) {
            //création d'un logger pour la classe courante
            Logger log = LogManager.GetCurrentClassLogger();

            //if (e.GetType().ToString() == "AgenceUtils.ExceptionMetier") {
            if (e.GetType() == typeof (AgenceUtils.ExceptionMetier)) {
                //log de l'exception, type "warning"
                log.LogException(LogLevel.Warn, "Exception métier", e);
            }
            else {
                //log de l'exception
                log.LogException(LogLevel.Fatal, "Exception système", e);
            }
        }
    }
}
