using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace AgenceEntites
{
    public class EntiteBase : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged<T>(Expression<Func<T>> expression) {
            var memberExpression = expression.Body as MemberExpression;

            if (memberExpression != null) {
                string propertyName = memberExpression.Member.Name;
                PropertyChangedEventHandler handler = PropertyChanged;

                if (handler != null)
                    PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
    }


}
