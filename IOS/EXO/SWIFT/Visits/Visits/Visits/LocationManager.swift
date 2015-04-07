//
//  TaskManager.swift
//  ToDo
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData
import MapKit

class LocationManager : DataManager{
    
    override class var SharedManager: LocationManager {
        struct Singleton{
            static let instance = LocationManager()
        }
        
        return Singleton.instance
    }
    
    //MARK: Persistence
    func createLocationForName(name : String?, withRange range : Double?, andCoordinate coor : CLLocationCoordinate2D?)->Location?{
        if let nameValue = name{
            if let rangeValue = range{
                if let coorValue = coor{
                    let entity = NSEntityDescription.entityForName("Location", inManagedObjectContext: manageObjectContext)
                    let location = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: manageObjectContext) as Location
                    
                    location.name = nameValue
                    
                    var error : NSError? = nil
                    
                    location.managedObjectContext?.save(&error)
                    
                    if error != nil{
                        println("Could not save context : \(error), \(error?.description)")
                    }
                    
                    return location
                }
            }
        }
        
        return nil
    }
    
    func loadData() -> [Location]? {
        let fetchRequest = NSFetchRequest(entityName: "Location")
        
        var error : NSError? = nil
        
        if let results = manageObjectContext.executeFetchRequest(fetchRequest, error: &error) as? [Location]{
            return results
        }
        
        if error != nil{
            println("Could not fetch data : \(error), \(error?.description)")
        }
        
        return nil
    }

}
