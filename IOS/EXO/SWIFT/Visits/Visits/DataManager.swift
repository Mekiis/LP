//
//  DataManager.swift
//  ToDo
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class DataManager {
    
    let manageObjectContext: NSManagedObjectContext
    let persistentStore: NSPersistentStore?
    let persistentStoreCoordinator: NSPersistentStoreCoordinator
    let manageObjectModel: NSManagedObjectModel
    
    class var SharedManager: DataManager {
        struct Singleton{
            static let instance = DataManager()
        }
        
        return Singleton.instance
    }
    
    func applicationDocumentDirectory() -> NSURL{
        let fileManager = NSFileManager.defaultManager()
        let urls = fileManager.URLsForDirectory(NSSearchPathDirectory.DocumentDirectory, inDomains: NSSearchPathDomainMask.UserDomainMask)
        
        return urls[0] as NSURL
    }
    
    init(){
        let modelURL = NSBundle.mainBundle().URLForResource("Visits", withExtension: "momd")!
        
        manageObjectModel = NSManagedObjectModel(contentsOfURL: modelURL)!
        persistentStoreCoordinator = NSPersistentStoreCoordinator(managedObjectModel: manageObjectModel)
        manageObjectContext = NSManagedObjectContext()
        manageObjectContext.persistentStoreCoordinator = persistentStoreCoordinator
        
        let documentURL = applicationDocumentDirectory()
        let storeURL = documentURL.URLByAppendingPathComponent("Visits.sqlite")
        let options = [NSMigratePersistentStoresAutomaticallyOption: true]
        
        var error: NSError? = nil
        persistentStore = persistentStoreCoordinator.addPersistentStoreWithType(
            NSSQLiteStoreType,
            configuration: nil,
            URL: storeURL,
            options: options,
            error: &error)
        
        if persistentStore == nil{
            println("Error adding persistence store: \(error)")
            abort()
        }
    }
    
    func saveContext(){
        var error: NSError? = nil
        
        if manageObjectContext.hasChanges && !manageObjectContext.save(&error){
            println("Failed to save context: \(error), \(error?.userInfo)")
        }
    }
}
