//
//  AddCocktailViewController.swift
//  Cocktail
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class AddCocktailViewController: UIViewController {

    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var ingredientsField: UITextView!
    @IBOutlet weak var directionsField: UITextView!
    
    var completionHandler:((CocktailDAO.Result) ->())?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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

    @IBAction func quitWithoutSaving(sender: AnyObject) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func saveCocktailDetails(sender: AnyObject) {
        if(nameField.text.isEmpty || ingredientsField.text.isEmpty || directionsField.text.isEmpty){
            completionHandler?(CocktailDAO.Result.Cancel)
            self.dismissViewControllerAnimated(true, completion: nil)
        } else {
            let cocktail = Cocktail(name: nameField.text, ingredients: ingredientsField.text, directions: directionsField.text)
            CocktailDAO.getInstance().addData(cocktail)
            completionHandler?(CocktailDAO.Result.Saved)
            self.dismissViewControllerAnimated(true, completion: nil)
        }
    }
}
