//
//  AddLocationViewController.swift
//  Visits
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit
import MapKit

class AddLocationViewController: UIViewController, CLLocationManagerDelegate, MKMapViewDelegate{
    
    @IBOutlet weak var map: MKMapView!
    @IBOutlet weak var sliderRange: UISlider!
    
    var searchRadius: CLLocationDistance = 250
    var pin : PinLocation?
    var circle : MKCircle?
    var cllocationManager : CLLocationManager!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if CLLocationManager.locationServicesEnabled(){
            cllocationManager = CLLocationManager()
            cllocationManager.delegate = self
            cllocationManager.desiredAccuracy = kCLLocationAccuracyBest
            cllocationManager.requestAlwaysAuthorization()
            cllocationManager.startUpdatingLocation()
        }
        self.map.delegate = self
    }
    
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!) {
        centerMapOnLocation(locations.last as CLLocation)
        cllocationManager.stopUpdatingLocation()
    }
    
    @IBAction func onTapped(sender: UITapGestureRecognizer) {
        let coor = map.convertPoint(sender.locationInView(sender.view), toCoordinateFromView: sender.view)
        
        addPin(CLLocation(latitude: coor.latitude, longitude: coor.longitude));
        addRadiusCircle(CLLocation(latitude: coor.latitude, longitude: coor.longitude))
        centerMapOnLocation(CLLocation(latitude: coor.latitude, longitude: coor.longitude))
    }
    
    func mapView(mapView: MKMapView!, rendererForOverlay overlay: MKOverlay!) -> MKOverlayRenderer! {
        if overlay is MKCircle {
            var circle = MKCircleRenderer(overlay: overlay)
            circle.strokeColor = UIColor.redColor()
            circle.fillColor = UIColor(red: 255, green: 0, blue: 0, alpha: 0.1)
            circle.lineWidth = 1
            return circle
        } else { return nil }
    }
    
    @IBAction func saveLocation(sender: AnyObject) {
        let alert = UIAlertController(title: "Location name", message: "Add name for this location", preferredStyle: UIAlertControllerStyle.Alert)
        
        let saveAction = UIAlertAction(title: "Save", style: UIAlertActionStyle.Default) { (_) -> Void in
            let textField = alert.textFields![0] as UITextField
            
        }
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil)
        
        alert.addTextFieldWithConfigurationHandler (nil)
        
        alert.addAction(saveAction)
        alert.addAction(cancelAction)
        
        presentViewController(alert, animated: true, completion: nil)
    }
    

    
    @IBAction func cancelLocation(sender: AnyObject) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    //MARK: Map display management
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegionMakeWithDistance(location.coordinate,
            searchRadius * 2.0, searchRadius * 2.0)
        map.setRegion(coordinateRegion, animated: true)
    }
    
    func addPin(location: CLLocation){
        if self.pin != nil {
            self.map.removeAnnotation(pin)
        }
        
        let coor = CLLocationCoordinate2D(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        let title = ""
        let name = ""
        // Todo Add a location name / Title
        pin = PinLocation(title: title,
            name: name,
            coordinate: coor)
        
        map.addAnnotation(pin)
    }

    @IBAction func rangeChanged(sender: UISlider) {
        if let unwrappedPin = self.pin {
            let loc = CLLocation(latitude: unwrappedPin.coordinate.latitude, longitude: unwrappedPin.coordinate.longitude)
            addRadiusCircle(loc)
        }
    }
    
    func addRadiusCircle(location: CLLocation){
        if circle != nil{
            self.map.removeOverlay(circle)
        }
        
        circle = MKCircle(centerCoordinate: location.coordinate, radius: 10 as CLLocationDistance)
        self.map.addOverlay(circle)
    }
}