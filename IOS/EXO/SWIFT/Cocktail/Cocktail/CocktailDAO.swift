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
        
        return cocktails!
    }
    
    func saveData(){
        var root2 = [[String: String]]()
        let index:Int! = 0
        for cocktail in cocktails{
            var dict = [String: String]()
            dict["name"] = cocktails[index].name
            dict["ingredients"] = cocktails[index].ingredients
            dict["directions"] = cocktails[index].directions
            root2.append(dict)
        }
        
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths.objectAtIndex(0) as NSString
        let path = NSBundle.mainBundle().pathForResource("DrinkDirections", ofType: "plist")
    }
    
    func deleteData(cocktail:Cocktail){
        if(getIndexFor(cocktail) != -1){
            cocktails.removeAtIndex(getIndexFor(cocktail))
        }
        
    }
    
    func getIndexFor(cocktail:Cocktail)->Int{
        var i:Int
        for i = 0; i < cocktails.count; ++i {
            if(    cocktails[i].name == cocktail.name
                && cocktails[i].directions == cocktail.directions
                && cocktails[i].ingredients == cocktail.ingredients){
                return i
            }
        }
        
        return -1
    }
    
    func modify(oldCocktail:Cocktail, withNewCocktail:Cocktail){
        if(getIndexFor(oldCocktail) != -1){
            cocktails[getIndexFor(oldCocktail)] = withNewCocktail
        }
    }
    
    func addData(cocktail:Cocktail){
        cocktails.append(cocktail)
    }
    
    func addData(cocktail:Cocktail, atLocation:NSInteger){
        cocktails.insert(cocktail, atIndex: atLocation)
    }
    
}
