//
//  Location.swift
//  Visits
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData
import MapKit

class Location: NSManagedObject {
    @NSManaged var title: String
    @NSManaged var name: String
    @NSManaged var coordinate: CLLocationCoordinate2D
    
    func initialize(title: String, name: String, coordinate: CLLocationCoordinate2D) {
        self.title = title
        self.name = name
        self.coordinate = coordinate
    }
}
