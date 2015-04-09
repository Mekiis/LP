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
    
    @IBOutlet weak var map: MKMapView!
    
    override func viewDidLoad() {
        super.viewDidLoad() 
        self.title = type
        
        places = PlaceManager(coreDataManager: DataManager.SharedManager).getPlacesByType(type)

        let annotationView = MKAnnotationView()
        let detailButton: UIButton = UIButton.buttonWithType(UIButtonType.DetailDisclosure) as UIButton
        annotationView.rightCalloutAccessoryView = detailButton
        
        for place in places {
            addPin(place)
        }
        
        self.map.delegate = self
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
        
        let reuseId = "pin"
        var pinView = mapView.dequeueReusableAnnotationViewWithIdentifier(reuseId) as? MKPinAnnotationView
        
        if pinView == nil {
            //println("Pinview was nil")
            pinView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: reuseId)
            pinView!.canShowCallout = true
            pinView!.animatesDrop = true
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
        }
    }
    

}
