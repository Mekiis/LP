//
//  PlacesOnMapViewController.swift
//  Visits
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import UIKit
import CoreLocation
import MapKit

class PlacesOnMapViewController: UIViewController, MKMapViewDelegate {
    
    var type : String!
    var places : [Place]! = [Place] ()
    var location : Location!
    
    var completionHandler : (() -> ())?
    
    @IBOutlet weak var map: MKMapView!
    
    override func viewDidLoad() {
        super.viewDidLoad() 
        self.title = type
        
        places = PlaceManager(coreDataManager: DataManager.SharedManager).getPlacesByType(type, inLocation : location)

        let annotationView = MKAnnotationView()
        let detailButton: UIButton = UIButton.buttonWithType(UIButtonType.DetailDisclosure) as UIButton
        annotationView.rightCalloutAccessoryView = detailButton
        
       addAllPlaces(andCenter: true)
        
        self.map.delegate = self
    }
    
    func addAllPlaces(andCenter needCenter : Bool){
        var latitude : Double = 0
        var longitude : Double = 0
        var isInitialized : Bool = false
        
        for place in places {
            addPin(place)
            if !isInitialized {
                latitude = place.latitude
                longitude = place.longitude
                isInitialized = true
            }else{
                latitude += place.latitude
                longitude += place.longitude
            }
        }
        if isInitialized && needCenter{
            latitude = latitude / Double(places.count)
            longitude = longitude / Double(places.count)
            centerMapOnLocation(CLLocation(latitude: latitude, longitude: longitude))
        }

    }
    
    func refreshAllPin(){
        map.removeAnnotations(map.annotations)
        addAllPlaces(andCenter: false)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func addPin(place: Place){
        
        var pin : PinPlace = PinPlace()
        pin.setCoordinate(CLLocationCoordinate2D(latitude : place.latitude, longitude : place.longitude))
        pin.title = place.name
        pin.subtitle = place.address
        pin.place = place
        
        map.addAnnotation(pin)
    }
    
    func centerMapOnLocation(location : CLLocation){
        let center = CLLocationCoordinate2D(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        
        var mapCamera = MKMapCamera(lookingAtCenterCoordinate: center, fromEyeCoordinate: center, eyeAltitude: 4000)
        map.setCamera(mapCamera, animated: true)
    }
    
    // When user taps on the disclosure button you can perform a segue to navigate to another view controller
    func mapView(mapView: MKMapView!, annotationView view: MKAnnotationView!, calloutAccessoryControlTapped control: UIControl!) {
        if control == view.rightCalloutAccessoryView{
            if let pin = view.annotation as? PinPlace {
                println("\(pin.place!.name)") // annotation's title
                println(view.annotation.subtitle) // annotation's subttitle
                performSegueWithIdentifier("PlaceDetail", sender: pin)
            }
            
            
        }
    }
        
    // Here we add disclosure button inside annotation window
    func mapView(mapView: MKMapView!, viewForAnnotation annotation: MKAnnotation!) -> MKAnnotationView! {
  
        if annotation is MKUserLocation {
            return nil
        }
        
        var reuseId = "pin"
        var imageSource = ""
        if let pin = annotation as? PinPlace{
            if pin.place?.mark > 0 {
                reuseId = "pinVisited"
                imageSource = "green_pin.png"
            }else{
                reuseId = "pinNotVisited"
                imageSource = "blue_pin.png"
            }
        }
        
        var pinView = mapView.dequeueReusableAnnotationViewWithIdentifier(reuseId) as? MKPinAnnotationView
        
        if pinView == nil {
            pinView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: reuseId)
            pinView!.canShowCallout = true
            pinView!.animatesDrop = false
            pinView!.image = UIImage(named: imageSource)
        }
        
        var button = UIButton.buttonWithType(UIButtonType.DetailDisclosure) as UIButton // button with info sign in it
        
        pinView?.rightCalloutAccessoryView = button
                
        return pinView
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "PlaceDetail"{
            let pdv = segue.destinationViewController as PlaceDetailViewController
            pdv.place = (sender as PinPlace).place
            pdv.completionHandler = {() -> () in
                self.refreshAllPin()
            }
        }
    }
    
    override func didMoveToParentViewController(parent: UIViewController?) {
        if parent == nil {
            completionHandler!()
        }
    }

}
