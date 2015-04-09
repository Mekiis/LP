//
//  GooglePlace.swift
//  Feed Me
//
//  Created by Ron Kliffer on 8/30/14.
//  Copyright (c) 2014 Ron Kliffer. All rights reserved.
//

import UIKit
import Foundation
import CoreLocation
import CoreData

let acceptedTypes = ["restaurant", "bar", "museum", "park"]

class Place : NSManagedObject{
  
    @NSManaged var id : String
    @NSManaged var name: String
    @NSManaged var address: String
    @NSManaged var latitude : Double
    @NSManaged var longitude : Double
    @NSManaged var placeType: String
    @NSManaged var photoReference: String
    @NSManaged var picture : NSData
    @NSManaged var mark : Double
    @NSManaged var locations : NSSet

}
