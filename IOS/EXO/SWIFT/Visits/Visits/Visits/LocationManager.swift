//
//  LocationManager.swift
//  Visites
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class LocationManager {
    
    var coreDataManager : DataManager?
    init(coreDataManager : DataManager){
        self.coreDataManager = coreDataManager
    }
    
    func createLocation(id: String!, name: String!, latitude: Double!, longitude: Double!, radius: Double!) -> Location {
        
        let entity = NSEntityDescription.entityForName("Location", inManagedObjectContext: coreDataManager!.managedObjectContext)
        
        let location = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: coreDataManager!.managedObjectContext) as Location
        
        //println("Could you not fetch data : \(id), \(name), \(latitude), \(longitude), \(radius)")
        
        location.id = id
        location.name = name
        location.latitude = latitude
        location.longitude = longitude
        location.radius = radius
        
        return location
    }
    
    func addLocation(newLocation : Location){
        var error : NSError? = nil
        newLocation.managedObjectContext?.save(&error)
        
        if(error != nil){
            println("Cannot save data : \(error),  \(error?.description)")
        }

    }
    
    func deleteLocation(location : Location?){
        if let locationToDelete = location{
            coreDataManager!.managedObjectContext.deleteObject(locationToDelete)
            coreDataManager!.managedObjectContext.save(nil)
        }
    }
    
    func loadLocation() -> [Location]?{
        
        let fetchRequest = NSFetchRequest(entityName: "Location")
        var error : NSError? = nil
        
        if let results = coreDataManager!.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as? [Location]{
            return results
        }
        
        if error != nil {
            println("Could you not fetch data : \(error),  \(error?.description)")
        }
        
        return nil
    }
    
    func updateLocation(oldLocation : Location, withLocation newLocation: Location){
        //TODO supprimer ref location dans toutes les places ?
        deleteLocation(oldLocation)
        addLocation(newLocation)
    }
}