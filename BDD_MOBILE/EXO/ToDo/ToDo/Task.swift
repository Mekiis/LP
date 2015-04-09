//
//  Task.swift
//  ToDo
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class Task: NSManagedObject {

    @NSManaged var name: String
    @NSManaged var text: String

}
