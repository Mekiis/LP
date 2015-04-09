//
//  Location.swift
//  Visites
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class Location : NSManagedObject{
    @NSManaged var id : String
    @NSManaged var latitude : Double
    @NSManaged var longitude : Double
    @NSManaged var name : String
    @NSManaged var radius : Double
    @NSManaged var places : NSSet

}