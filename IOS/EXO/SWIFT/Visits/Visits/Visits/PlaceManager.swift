//
//  LocationManager.swift
//  Visites
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData
import CoreLocation

class PlaceManager {
    
    var coreDataManager : DataManager?
    init(coreDataManager : DataManager){
        self.coreDataManager = coreDataManager
    }
    
    var places : [Place]?
    
    func updatePlaces(externalPlaces: [Place], inLocation location: Location){
        
        for place in externalPlaces{
            var placeInDB : Place = getPlaceById(place.id)!

            placeInDB.name = place.name
            placeInDB.address = place.address
            placeInDB.placeType = place.placeType
            placeInDB.photoReference = place.photoReference
            
            placeInDB.picture = place.picture
            placeInDB.mark = place.mark
                    
            var isInLocation : Bool = false
            var allLocations : NSMutableSet = placeInDB.mutableSetValueForKey("locations")
            for existingLocation in allLocations{
                if let locationValue = existingLocation as? Location{
                    if locationValue == location{
                        isInLocation = true
                        break
                    }
                }
            }
                    
            if !isInLocation{
                allLocations.addObject(location)
                placeInDB.locations = allLocations
            }
                    
            savePlace(place)
        
            //Add place in location
            var allPlaces : NSMutableSet = location.mutableSetValueForKey("places")
            var isInPlace : Bool = false
            for locationPlace in allPlaces {
                if locationPlace as Place == place {
                    isInPlace = true
                    break
                }
            }
            if !isInPlace{
                allPlaces.addObject(place)
                location.places = allPlaces
                location.managedObjectContext?.save(nil)
            }
        }
    }

    func getPlacesForLocation(location: Location) -> [Place]?{
        var fetchRequest = NSFetchRequest(entityName: "Place")
        let resultPredicate = NSPredicate(format: "%@ IN locations", location)
        fetchRequest.predicate = resultPredicate
        
        if let fetchResults = coreDataManager!.managedObjectContext.executeFetchRequest(fetchRequest, error: nil) as? [NSManagedObject]{
            if fetchResults.count != 0{
                return fetchResults as? [Place]
            }
        }
        
        return nil

    }
    
    func containPlace(place: Place, inPlaces places: [Place]) -> Place?{
        for placeValue in places{
            if placeValue == place {
                return placeValue
            }
        }
        return nil
    }
    
    func updatePlace(place: Place){
        var error : NSError? = nil
        place.managedObjectContext?.save(&error)
        
        if(error != nil){
            println("Cannot save data : \(error),  \(error?.description)")
        }
    }
    
    func getPlaceById(id : String) -> Place?{
        var fetchRequest = NSFetchRequest(entityName: "Place")
        let resultPredicate = NSPredicate(format: "id == %@", id)
        fetchRequest.predicate = resultPredicate
            
        if let fetchResults = coreDataManager!.managedObjectContext.executeFetchRequest(fetchRequest, error: nil) as? [NSManagedObject]{
            if fetchResults.count != 0{
                return fetchResults[0] as? Place
            }
        }
        
        return nil
    }
    
    func createPlace(rawPlaces : NSDictionary) -> Place{
        let entity = NSEntityDescription.entityForName("Place", inManagedObjectContext: coreDataManager!.managedObjectContext)
        
        let place = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: coreDataManager!.managedObjectContext) as Place
        
        //println("Could you not fetch data : \(id), \(name), \(latitude), \(longitude), \(radius)")
        
        place.id = rawPlaces["place_id"] as String
        place.name = rawPlaces["name"] as String
        place.address = rawPlaces["vicinity"] as String
        
        let location = rawPlaces["geometry"]?["location"] as NSDictionary
        place.latitude = location["lat"] as CLLocationDegrees
        place.longitude = location["lng"] as CLLocationDegrees
        
        if let photos = rawPlaces["photos"] as? NSArray {
            let photo = photos.firstObject as NSDictionary
            place.photoReference = photo["photo_reference"] as NSString
        }else{
            place.photoReference = ""
        }
        
        var foundType = "restaurant"
        let possibleTypes = acceptedTypes
        for type in rawPlaces["types"] as [String] {
            if contains(possibleTypes, type) {
                foundType = type
                break
            }
        }
        place.placeType = foundType
        place.mark = 0.0

        
        return place

    }
    
    func savePlace(newPlace: Place) {
        var error : NSError? = nil
        newPlace.managedObjectContext?.save(&error)
        
        if(error != nil){
            println("Cannot save data : \(error),  \(error?.description)")
        }
        
    }
   
//    func deletePlace(place : Place?){
//        if let placeToDelete = place{
//            self.managedObjectContext.deleteObject(placeToDelete)
//            self.managedObjectContext.save(nil)
//        }
//    }
    
    func getPlacesByType(type : String, inLocation : Location)->[Place]{
       
        var filteredPlaces : [Place] = [Place]()
        if let placesValues = getPlacesForLocation(inLocation) {
            for place in placesValues {
                if place.placeType == type{
                    filteredPlaces.append(place)
                }
            }
        }
        return filteredPlaces
    }
    
    func loadPlace() -> [Place]{
        
        let fetchRequest = NSFetchRequest(entityName: "Place")
        var error : NSError? = nil
        
        if let results = coreDataManager!.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as? [Place]{
            return results
        }
        
        if error != nil {
            println("Could you not fetch data : \(error),  \(error?.description)")
        }
        
        return [Place]()
    }
}