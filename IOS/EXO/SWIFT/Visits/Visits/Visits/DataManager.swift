//
//  DataManager.swift
//  Todo
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation
import CoreData

class DataManager {
    let managedObjectContext : NSManagedObjectContext
    let persistentStore : NSPersistentStore?
    let persistentStoreCoordinator : NSPersistentStoreCoordinator
    let managedObjectModel : NSManagedObjectModel
    
    class var SharedManager : DataManager {
        struct Singleton {
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
        let modeURL = NSBundle.mainBundle().URLForResource("Visites", withExtension: "momd")!
        
        managedObjectModel = NSManagedObjectModel(contentsOfURL: modeURL)!
        
        persistentStoreCoordinator = NSPersistentStoreCoordinator(managedObjectModel: managedObjectModel)
        
        managedObjectContext = NSManagedObjectContext()
        managedObjectContext.persistentStoreCoordinator = persistentStoreCoordinator
        
        let documentUrl = applicationDocumentDirectory()
        let storeURL = documentUrl.URLByAppendingPathComponent("Visites.sqlite")
        let options = [NSMigratePersistentStoresAutomaticallyOption : true]
        
        var error : NSError? = nil
        persistentStore = persistentStoreCoordinator.addPersistentStoreWithType(
            NSSQLiteStoreType,
            configuration: nil,
            URL: storeURL,
            options: options,
            error: &error)
        
        if persistentStore == nil {
            println("Error aborting persistent store: \(error)")
            abort()
        }
    }
    
    func saveContext(){
        var error: NSError? = nil
        if managedObjectContext.hasChanges && !managedObjectContext.save(&error){
            println("Failed to save context : \(error), \(error?.userInfo)")
        }
    }
    
}