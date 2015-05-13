//
//  PlacesToSeeViewController.swift
//  Visites
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit
import Foundation
import CoreLocation
import MapKit

class PlacesToSeeViewController: UIViewController, NSURLConnectionDataDelegate, UITableViewDelegate, UITableViewDataSource {
        
    @IBOutlet var tableView: UITableView!
    
    var location : Location!
    var types : [TypePlace] = [TypePlace]()
    
    let dataProvider = GoogleDataProvider()
        
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataProvider.fetchPlacesNearCoordinate(CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude), radius:location.radius, types:acceptedTypes) { placesByGoogle in
            if placesByGoogle.count != 0{
                PlaceManager(coreDataManager: DataManager.SharedManager).updatePlaces(placesByGoogle, inLocation: self.location)
            
            }
            
            if let placesUpdated = PlaceManager(coreDataManager: DataManager.SharedManager).getPlacesForLocation(self.location){
                for place: Place in placesUpdated {
                    //Construct type list
                    if !self.containsType(place.placeType) {
                        self.types.append(TypePlace(name: place.placeType, number: 1, visited: 0))
                    }else{
                        for type: TypePlace in self.types{
                            if type.name == place.placeType{
                                type.number = type.number!+1
                            }
                        }
                    }
                }
            }
            self.majTypeVisted()
            self.tableView.reloadData()
        }
    }
    
    func majTypeVisted(){
        for type in types{
            var places : [Place] = PlaceManager(coreDataManager: DataManager.SharedManager).getPlacesByType(type.name!, inLocation: location)
            var placesVisited : Int = 0
            for place in places {
                if place.mark > 0 {
                    placesVisited++
                }
            }
            type.visited = placesVisited
        }
        
    }
    
    func containsType(name: String) -> Bool{
        for type: TypePlace in types {
            if type.name == name {
               return true
            }
        }
        return false
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.types.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("place", forIndexPath: indexPath) as UITableViewCell
        
        if let cellType = cell as? PlaceTypeTableViewCell{
            cellType.title.text = self.types[indexPath.row].name?.capitalizedString
            cellType.subTitle.text = String(types[indexPath.row].visited!.hashValue) + "/" + String(types[indexPath.row].number!.hashValue)
            cellType.quantity.changeImage(UIImage(named: self.types[indexPath.row].name! + "_icon.png")!)
            cellType.quantity.changeRating(CGFloat(types[indexPath.row].getRatioVisited()!))
        }
        
        return cell
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "GoToSeePlacesOnMap" {
            let pom = segue.destinationViewController as PlacesOnMapViewController
            let indexPath = self.tableView.indexPathForSelectedRow()!
            pom.type = types[indexPath.row].name
            pom.location = self.location
            pom.completionHandler = {() -> () in
                self.majTypeVisted()
                self.tableView.reloadData()
            }
        }
    }
    
    @IBAction func sortByName(sender: AnyObject) {
        self.types.sort({
            $0.name < $1.name
        })
        self.tableView.reloadData()
    }
    
    @IBAction func SortByNumber(sender: AnyObject) {
        self.types.sort({
            $0.number > $1.number
        })
        self.tableView.reloadData()
    }
    
}
