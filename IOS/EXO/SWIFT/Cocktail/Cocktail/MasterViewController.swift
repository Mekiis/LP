//
//  ViewController.swift
//  Cocktail
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class MasterViewController: UITableViewController {
    
    var cocktails : [Cocktail]!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
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

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cocktails.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("CocktailCell") as  UITableViewCell
        
        let cocktail = cocktails[indexPath.row]
        cell.textLabel.text = cocktail.name
        
        return cell
    }

}

