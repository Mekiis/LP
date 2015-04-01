//
//  TaskManager.swift
//  ToDo
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class TaskManager : DataManager{
    
    override class var SharedManager: TaskManager {
        struct Singleton{
            static let instance = TaskManager()
        }
        
        return Singleton.instance
    }
    
    //MARK: Persistence
    func createTaskForName(name : String?)->Task?{
        if let nameValue = name{
            let entity = NSEntityDescription.entityForName("Task", inManagedObjectContext: manageObjectContext)
            let task = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: manageObjectContext) as Task
            
            task.name = nameValue
            
            var error : NSError? = nil
            
            task.managedObjectContext?.save(&error)
            
            if error != nil{
                println("Could not save context : \(error), \(error?.description)")
            }
            
            return task
        }
        
        return nil
    }
    
    func loadData() -> [Task]? {
        let fetchRequest = NSFetchRequest(entityName: "Task")
        
        var error : NSError? = nil
        
        if let results = manageObjectContext.executeFetchRequest(fetchRequest, error: &error) as? [Task]{
            return results
        }
        
        if error != nil{
            println("Could not fetch data : \(error), \(error?.description)")
        }
        
        return nil
    }

}
