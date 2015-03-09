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

    override func viewDidLoad() {
        super.viewDidLoad()
        nameField.text = cocktail.name
        directionsField.text = cocktail.directions
        ingredientsField.text = cocktail.ingredients
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
