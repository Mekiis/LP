using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Data;

namespace APIs_spéciale.Converters
{
    class BoolToVisibilityConverter : IValueConverter
    {
        public enum BooleanToVisibilityParameter
        {
            Normal = 0,
            Invert = 1,
        }

        public object Convert(object value, Type targetType, object parameter, string language)
        {
            parameter = GetParameterValue(parameter);
            if ((bool)(value))
            {
                if ((((BooleanToVisibilityParameter)(parameter)) == BooleanToVisibilityParameter.Invert))
                {
                    return Visibility.Collapsed;
                }
                return Visibility.Visible;
            }
            if ((((BooleanToVisibilityParameter)(parameter)) == BooleanToVisibilityParameter.Invert))
            {
                return Visibility.Visible;
            }
            return Visibility.Collapsed;
        }

        public object ConvertBack(object value, Type targetType, object parameter, string language)
        {
            switch (((Visibility)(value)))
            {
                case Visibility.Collapsed: break;
                
                case Visibility.Visible:
                    if ((((BooleanToVisibilityParameter)(parameter)) == BooleanToVisibilityParameter.Invert))
                    {
                        return false;
                    }
                    return true;
                    break;
            }
            if ((((BooleanToVisibilityParameter)(parameter)) == BooleanToVisibilityParameter.Invert))
            {
                return true;
            }
            return false;
        }

        private BooleanToVisibilityParameter GetParameterValue(object parameter)
        {
            if (parameter != null)
            {
                return ((BooleanToVisibilityParameter)(int.Parse((string)(parameter))));
            }
            return BooleanToVisibilityParameter.Normal;
        }

    }
}
