using System;
using System.Threading.Tasks;
using Windows.Data.Xml.Dom;
using Windows.UI.Notifications;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Media.Animation;

namespace AgenceRT.Common {
    class UtilsUI {

        public static void ToastMessage(string message) {
            string toastText = message;
            var toastTemplate = ToastNotificationManager.GetTemplateContent(ToastTemplateType.ToastImageAndText01);

            var text = toastTemplate.GetElementsByTagName("text")[0] as XmlElement;
            text.AppendChild(toastTemplate.CreateTextNode(toastText));

            var image = toastTemplate.GetElementsByTagName("image")[0] as XmlElement;
            //image.SetAttribute("src", "ms-appx:///Images/location.png");

            ToastNotification toastNotification = new ToastNotification(toastTemplate);
            ToastNotifier toastNotifier = ToastNotificationManager.CreateToastNotifier();
            toastNotification.ExpirationTime = DateTime.Now.AddSeconds(20);
            toastNotifier.Show(toastNotification);
        }
    }
}
