//
//  CocktailDAO.swift
//  Cocktail
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation

var instance:CocktailDAO?

class CocktailDAO{
    
    enum Result{
        case Saved
        case Cancel
    }
    
    var cocktails:[Cocktail]!
    
    class func getInstance()->CocktailDAO{
        if instance == nil{
            instance = CocktailDAO()
        }
        
        return instance!
    }
    
    func getDatas()->[Cocktail]{
        
        /*
        if(cocktails == nil){
            if let path = NSBundle.mainBundle().pathForResource("DrinkDirections", ofType: "plist"){
                if let cocktailsArray = NSArray(contentsOfFile: path) as? [[String : String]] {
                    cocktails = [Cocktail]()
                    for dict in cocktailsArray{
                        let name = dict["name"]!
                        let ingredients = dict["ingredients"]!
                        let directions = dict["directions"]!
                        cocktails.append(Cocktail(name: name, ingredients: ingredients, directions: directions))
                    }
                }
            }
        }
        */
        
        
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths.objectAtIndex(0)as NSString
        let path = documentsDirectory.stringByAppendingPathComponent("DrinkDirections.plist")
        
        let fileManager = NSFileManager.defaultManager()
        
        // Check if file exists
        if(!fileManager.fileExistsAtPath(path))
        {
            // If it doesn't, copy it from the default file in the Resources folder
            let bundle = NSBundle.mainBundle().pathForResource("DrinkDirections", ofType: "plist")
            fileManager.copyItemAtPath(bundle!, toPath: path, error:nil)
        }
        
        if let cocktailsArray = NSArray(contentsOfFile: path) as? [[String : String]] {
            cocktails = [Cocktail]()
            for dict in cocktailsArray{
                    let name = dict["name"]!
                    let ingredients = dict["ingredients"]!
                    let directions = dict["directions"]!
                cocktails.append(Cocktail(name: name, ingredients: ingredients, directions: directions))
            }
        }
        
        
        return cocktails!
    }
    
    func saveData(){
        var root2 = [[String: String]]()
        for cocktail in cocktails{
            var dict = [String: String]()
            dict["name"] = cocktail.name
            dict["ingredients"] = cocktail.ingredients
            dict["directions"] = cocktail.directions
            root2.append(dict)
        }
        
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths.objectAtIndex(0) as NSString
        let path = documentsDirectory.stringByAppendingPathComponent("DrinkDirections.plist")
        
        (root2 as NSArray).writeToFile(path, atomically: true)
    }
    
    func deleteData(cocktail:Cocktail){
        if(getIndexFor(cocktail) != -1){
            cocktails.removeAtIndex(getIndexFor(cocktail))
            saveData()
        }
    }
    
    func getIndexFor(cocktail:Cocktail)->Int{
        if let index = find(cocktails, cocktail) {
            return index
        } else {
            return -1
        }
    }
    
    func modify(oldCocktail:Cocktail, withNewCocktail:Cocktail){
        if(getIndexFor(oldCocktail) != -1){
            cocktails[getIndexFor(oldCocktail)] = withNewCocktail
            saveData()
        }
    }
    
    func addData(cocktail:Cocktail){
        cocktails.append(cocktail)
        cocktails.sort{(c1,c2) in c1.name.lowercaseString < c2.name.lowercaseString}
        saveData()
    }
    
    func addData(cocktail:Cocktail, atLocation:NSInteger){
        cocktails.insert(cocktail, atIndex: atLocation)
        saveData()
    }
    
}
