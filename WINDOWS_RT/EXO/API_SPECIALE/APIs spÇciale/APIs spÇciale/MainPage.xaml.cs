using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Linq.Expressions;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// Pour en savoir plus sur le modèle d'élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkId=234238

namespace APIs_spéciale
{
    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    public sealed partial class MainPage : Page, INotifyPropertyChanged
    {

        #region NotifyPropertyChanged
        
        public event PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged<T>(Expression<Func<T>> expression)
        {
            var memberExpression = expression.Body as MemberExpression;

            if (memberExpression != null)
            {
                string propertyName = memberExpression.Member.Name;
                PropertyChangedEventHandler handler = PropertyChanged;

                if (handler != null)
                    PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        #endregion

        #region Geolocation

        private Windows.Devices.Geolocation.Geolocator _locator;

        private bool _location_loaded = false;
        public bool LocationLoaded
        {
            get { return _location_loaded; }
            set
            {
                if (_location_loaded != value)
                {
                    _location_loaded = value;
                    RaisePropertyChanged(() => LocationLoaded);
                }
            }
        }

        private double _latitude = double.NaN;
        public double Latitude
        {
            get { return _latitude; }
            set
            {
                if (_latitude != value)
                {
                    _latitude = value;
                    RaisePropertyChanged(() => Latitude);
                }
            }
        }

        private double _longitude = double.NaN;
        public double Longitude
        {
            get { return _longitude; }
            set
            {
                if (_longitude != value)
                {
                    _longitude = value;
                    RaisePropertyChanged(() => Longitude);
                }
            }
        }

        private string _adresse = string.Empty;
        public string Adresse
        {
            get { return _adresse; }
            set
            {
                if (_adresse != value)
                {
                    _adresse = value;
                    RaisePropertyChanged(() => Adresse);
                }
            }
        }
        
        #endregion

        #region Webcam

        private Windows.UI.Xaml.Media.Imaging.WriteableBitmap _photo = null;
        private Windows.Media.Capture.MediaCapture _media_capture = null;

        private Windows.Devices.Enumeration.DeviceInformationCollection _webcam_list;
        public Windows.Devices.Enumeration.DeviceInformationCollection WebcamList
        {
            get { return _webcam_list; }
            set
            {
                if (_webcam_list != value)
                {
                    _webcam_list = value;
                    this.SelectedWebcam = null;
                    RaisePropertyChanged(() => WebcamList);
                }
            }
        }

        private bool _webcam_list_loaded = false;
        public bool WebcamListLoaded
        {
            get { return _webcam_list_loaded; }
            set
            {
                if (_webcam_list_loaded != value)
                {
                    _webcam_list_loaded = value;
                    RaisePropertyChanged(() => WebcamListLoaded);
                }
            }
        }

        private Windows.Devices.Enumeration.DeviceInformation _selected_webcam = null;
        public Windows.Devices.Enumeration.DeviceInformation SelectedWebcam
        {
            get { return _selected_webcam; }
            set
            {
                if (_selected_webcam != value)
                {
                    ReleaseWebcam();
                    _selected_webcam = value;
                    RaisePropertyChanged(() => SelectedWebcam);
                    if (_selected_webcam != null) InitializeWebcam();
                }
            }
        }

        private bool _selected_webcam_initialized = false;
        public bool SelectedWebcamInitialized
        {
            get { return _selected_webcam_initialized; }
            set
            {
                if (_selected_webcam_initialized != value)
                {
                    _selected_webcam_initialized = value;
                    RaisePropertyChanged(() => SelectedWebcamInitialized);
                }
            }
        }

        public bool PhotoTaken
        {
            get { return _photo != null; }
            set
            {
            }
        }

        private string _photo_string = string.Empty;
        public string PhotoString
        {
            get { return _photo_string; }
            set
            {
                if (_photo_string != value)
                {
                    _photo_string = value;
                    RaisePropertyChanged(() => PhotoString);
                }
            }
        }

        #endregion



        public MainPage()
        {
            this.InitializeComponent();
            this.DataContext = this;

            LoadLocation();
            LoadWebcamList();
        }



        private async void LoadLocation()
        {
            if (this.LocationLoaded) return;

            await System.Threading.Tasks.Task.Delay(2000);

            _locator = new Windows.Devices.Geolocation.Geolocator();
            Windows.Devices.Geolocation.Geoposition location = null;
            location = await _locator.GetGeopositionAsync();
            this.Latitude = location.Coordinate.Point.Position.Latitude;
            this.Longitude = location.Coordinate.Point.Position.Longitude;
            this.Adresse = location.CivicAddress.PostalCode + " " + location.CivicAddress.City + "\r\n" + location.CivicAddress.Country; 
            this.LocationLoaded = true;
        }

        private async void LoadWebcamList()
        {
            if (this.WebcamListLoaded) return;

            await System.Threading.Tasks.Task.Delay(3000);

            this.WebcamList = await Windows.Devices.Enumeration.DeviceInformation.FindAllAsync(Windows.Devices.Enumeration.DeviceClass.VideoCapture);
            this.WebcamListLoaded = true;
        }

        private async void InitializeWebcam()
        {
            if (this.SelectedWebcamInitialized) return;
            
            _media_capture = new Windows.Media.Capture.MediaCapture();
            await _media_capture.InitializeAsync(new Windows.Media.Capture.MediaCaptureInitializationSettings
                                                                {
                                                                    VideoDeviceId = this.SelectedWebcam.Id,
                                                                    AudioDeviceId = "",
                                                                    StreamingCaptureMode =  Windows.Media.Capture.StreamingCaptureMode.Video,
                                                                    PhotoCaptureSource =  Windows.Media.Capture.PhotoCaptureSource.VideoPreview
                                                                });
            _media_capture.SetPreviewMirroring(false);


            this.captureElement.Source = _media_capture;

            await _media_capture.StartPreviewAsync();

            this.SelectedWebcamInitialized = true;
        }

        private void ReleaseWebcam()
        {
            if (!this.SelectedWebcamInitialized) return;
            if (_media_capture == null) return;

            _media_capture.Dispose();
            _media_capture = null;

            this.SelectedWebcamInitialized = false;
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            if (_photo != null)
            {
                if (await MessageBox.ShowAsync("Voulez-vous remplacer la photo actuelle ?",
                                               "Prendre une photo", MessageBoxButton.YesNo) == MessageBoxResult.No) return;
            }

            Windows.Media.MediaProperties.ImageEncodingProperties imageProperties = Windows.Media.MediaProperties.ImageEncodingProperties.CreateJpeg();

            var stream = new Windows.Storage.Streams.InMemoryRandomAccessStream();

            await _media_capture.CapturePhotoToStreamAsync(imageProperties, stream);

            _photo = new Windows.UI.Xaml.Media.Imaging.WriteableBitmap((int)this.captureElement.ActualWidth, (int)this.captureElement.ActualHeight);
            stream.Seek(0);
            await _photo.SetSourceAsync(stream);

            this.imageElement.Source = _photo;

            RaisePropertyChanged(() => PhotoTaken);
        }

        private async void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if (_photo == null)
            {
                this.PhotoString = "";
                return;
            }


            using (var writeStream = new Windows.Storage.Streams.InMemoryRandomAccessStream())
            {
                await EncodeWriteableBitmap(_photo, writeStream, Windows.Graphics.Imaging.BitmapEncoder.JpegEncoderId);
                this.PhotoString = await StreamToBase64(writeStream);
            }


        }

        private static async System.Threading.Tasks.Task EncodeWriteableBitmap(Windows.UI.Xaml.Media.Imaging.WriteableBitmap bmp, Windows.Storage.Streams.IRandomAccessStream writeStream, Guid encoderId)
        {
            // Copy buffer to pixels
            byte[] pixels;
            using (var stream = bmp.PixelBuffer.AsStream())
            {
                pixels = new byte[(uint)stream.Length];
                await stream.ReadAsync(pixels, 0, pixels.Length);
            }

            // Encode pixels into stream
            var encoder = await Windows.Graphics.Imaging.BitmapEncoder.CreateAsync(encoderId, writeStream);
            encoder.SetPixelData(Windows.Graphics.Imaging.BitmapPixelFormat.Bgra8, Windows.Graphics.Imaging.BitmapAlphaMode.Premultiplied,
               (uint)bmp.PixelWidth, (uint)bmp.PixelHeight,
               96, 96, pixels);
            await encoder.FlushAsync();
        }

        private async System.Threading.Tasks.Task<string> StreamToBase64(Windows.Storage.Streams.IRandomAccessStream fileStream)
        {
           string Base64String = "";
           var reader = new Windows.Storage.Streams.DataReader(fileStream.GetInputStreamAt(0));
           await reader.LoadAsync((uint)fileStream.Size);
           byte[] byteArray = new byte[fileStream.Size];
           reader.ReadBytes(byteArray);
           Base64String = Convert.ToBase64String(byteArray);
           return Base64String;
        }


    }
}
