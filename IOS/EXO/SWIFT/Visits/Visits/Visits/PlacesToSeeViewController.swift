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

class PlacesToSeeViewController: UITableViewController, NSURLConnectionDataDelegate, UITableViewDelegate, UITableViewDataSource {
        
    var location : Location!
    var types : [String] = [String]()
    // var places : [Place]?
    
    let dataProvider = GoogleDataProvider()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataProvider.fetchPlacesNearCoordinate(CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude), radius:location.radius, types:acceptedTypes) { places in
            for place: Place in places {
                //Construct type list
                if !contains(self.types, place.placeType){
                    self.types.append(place.placeType)
                }
            }
            
            PlaceManager(coreDataManager: DataManager.SharedManager).updatePlaces(places, inLocation: self.location)
            
            self.tableView.reloadData()
        }
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.types.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("place", forIndexPath: indexPath) as UITableViewCell
        
        cell.textLabel.text = self.types[indexPath.row]
        
        return cell
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "GoToSeePlacesOnMap" {
            let pom = segue.destinationViewController as PlacesOnMapViewController
            let indexPath = self.tableView.indexPathForSelectedRow()!
            pom.type = types[indexPath.row]
        }
    }
}
