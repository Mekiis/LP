//
//  ModifyCocktailViewController.swift
//  Cocktail
//
//  Created by iem on 12/03/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class ModifyCocktailViewController: UIViewController {
    
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var directionsField: UITextView!
    @IBOutlet weak var ingredientsField: UITextView!

    var cocktail:Cocktail!
    
    var completionHandler:((Bool, Cocktail) ->())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        nameField.text = cocktail.name
        directionsField.text = cocktail.directions
        ingredientsField.text = cocktail.ingredients
    }
    
    @IBAction func save(sender: AnyObject) {
        var newCocktail = Cocktail(name: nameField.text, ingredients: ingredientsField.text, directions: directionsField.text)
        CocktailDAO.getInstance().modify(cocktail, withNewCocktail: newCocktail)
        
        completionHandler?(true, newCocktail)
        
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func cancel(sender: AnyObject) {
        completionHandler?(false, cocktail)
        
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
}
