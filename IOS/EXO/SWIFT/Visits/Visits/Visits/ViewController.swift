//
//  ViewController.swift
//  Visites
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate, MKMapViewDelegate {

    @IBOutlet weak var map: MKMapView!
    var locationManager: CLLocationManager!
    var pin : MKPointAnnotation?
    var circle : MKCircle?
    var firstLocation : CLLocation?
    var radius : CLLocationDistance = 250
    
    var completionHandler : (() -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        if (CLLocationManager.locationServicesEnabled())
        {
            locationManager = CLLocationManager()
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestAlwaysAuthorization()
            locationManager.startUpdatingLocation()
        }
        self.map.showsUserLocation = true
        self.map.delegate = self
        
        self.navigationItem.rightBarButtonItem?.enabled = false
    }

    
    
    @IBAction func onRadiusSliderValueChanged(sender: UISlider) {
        if(pin != nil){
            radius = Double(sender.value)
            addRadiusCircle(CLLocation(latitude: pin!.coordinate.latitude, longitude: pin!.coordinate.longitude))
        }else{
            sender.value = 250
        }
    }
    
    @IBAction func onTapped(sender: UITapGestureRecognizer) {
        let location = map.convertPoint(
            sender.locationInView(sender.view),
            toCoordinateFromView: sender.view)
        addPin(location)
        self.navigationItem.rightBarButtonItem?.enabled = true
        
    }
    
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!){
        centerMapOnLocation(locations.last as CLLocation)
        locationManager.stopUpdatingLocation()
    }
    
    func addPin(location : CLLocationCoordinate2D){
        
        if(pin != nil){
            map.removeAnnotation(pin)
        }else{
            pin = MKPointAnnotation()
        }
        
        pin?.setCoordinate(location)
        pin?.title = ""
        pin?.subtitle = ""
        map.addAnnotation(pin)
        addRadiusCircle(CLLocation(latitude: location.latitude, longitude: location.longitude))
        centerMapOnLocation(CLLocation(latitude: location.latitude, longitude: location.longitude))
    }
    
    func addRadiusCircle(location: CLLocation){
        if circle != nil {
            map.removeOverlay(circle)
        }

         circle = MKCircle(centerCoordinate: location.coordinate, radius: radius)
        self.map.addOverlay(circle)
        
        
    }
    
    func centerMapOnLocation(location : CLLocation){
        
        let center = CLLocationCoordinate2D(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        let region = MKCoordinateRegion(center: center, span: MKCoordinateSpan(latitudeDelta: 0.01, longitudeDelta: 0.01))
        
        self.map.setRegion(region, animated: true)
    }
    
    //Mark : Map functions
    func mapView(mapView: MKMapView!, rendererForOverlay overlay: MKOverlay!) -> MKOverlayRenderer! {
        if overlay is MKCircle {
            var circle = MKCircleRenderer(overlay: overlay)
            circle.strokeColor = UIColor.redColor()
            circle.fillColor = UIColor(red: 255, green: 0, blue: 0, alpha: 0.1)
            circle.lineWidth = 1
            return circle
        } else {
            return nil
        }
    }
    
    @IBAction func cancelView(sender: UIBarButtonItem) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func saveLocation(sender: UIBarButtonItem) {
        if pin != nil{
            
            CLGeocoder().reverseGeocodeLocation(CLLocation(latitude: pin!.coordinate.latitude, longitude: pin!.coordinate.longitude), completionHandler:
                {(placemarks, error) in
                    if (error != nil) {
                        println("reverse geodcode fail: \(error.localizedDescription)")
                    } else {
                        var place : CLPlacemark?
                        let pm = placemarks as [CLPlacemark]
                        if pm.count > 0 {
                            place = (placemarks[0] as CLPlacemark)
                        }
                        let alert = UIAlertController(title: "New location", message: "Choose a name", preferredStyle: UIAlertControllerStyle.Alert)
                        
                        let saveAction = UIAlertAction(
                            title: "Save",
                            style: UIAlertActionStyle.Default)
                            { (_) -> Void in
                                let textField = alert.textFields![0] as UITextField
                                let uuid = NSUUID().UUIDString
                                LocationManager(coreDataManager: DataManager.SharedManager).createLocation(uuid, name: textField.text, latitude: self.pin!.coordinate.latitude, longitude: self.pin!.coordinate.longitude, radius: Double(self.radius))
                                
                                self.completionHandler?()
                                self.dismissViewControllerAnimated(true, completion: nil)
                        }
                        
                        let cancelAction = UIAlertAction(
                            title: "Cancel",
                            style: UIAlertActionStyle.Cancel)
                            { (_) -> Void in}
                        alert.addTextFieldWithConfigurationHandler { (textField : UITextField!) -> Void in
                            if let areasValues = place?.areasOfInterest as? [NSString] {
                                if areasValues.count > 0{
                                    textField.text = areasValues[0]
                                }
                            } else if let placeName = place?.locality {
                                textField.text = placeName
                            }
                        }
                        alert.addAction(saveAction)
                        alert.addAction(cancelAction)
                        
                        self.presentViewController(alert, animated: true, completion: nil)
                    }
            })
            
        } else {
            var alert : UIAlertView = UIAlertView(title: "Warning", message: "Select a location first", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
        }
        
    }

}

