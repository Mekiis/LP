//
//  DetailCocktailViewController.swift
//  Cocktail
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class DetailCocktailViewController: UIViewController {
    
    @IBOutlet weak var nameField: UILabel!
    @IBOutlet weak var directionsField: UITextView!
    @IBOutlet weak var ingredientsField: UITextView!
    
    var cocktail:Cocktail!
    var needReload:Bool! = false
    
    var completionHandler:((Bool) ->())?

    override func viewDidLoad() {
        super.viewDidLoad()
        displayDatas()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func displayDatas(){
        nameField.text = cocktail.name
        directionsField.text = cocktail.directions
        ingredientsField.text = cocktail.ingredients
    }
    
    override func viewWillDisappear(animated: Bool) {
        completionHandler?(needReload)
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "ToModifyCocktail"){
            let svc = segue.destinationViewController as ModifyCocktailViewController
            svc.cocktail = cocktail
            svc.completionHandler = { (needReload : Bool, newCocktail : Cocktail) -> () in
                if(needReload){
                    self.cocktail = newCocktail
                    self.displayDatas()
                    self.needReload = true
                }
            }
        }
    }

}
