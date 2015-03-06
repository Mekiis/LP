//
//  ViewController.swift
//  Cocktail
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class MasterViewController: UITableViewController {
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        CocktailDAO.getInstance().getDatas()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return CocktailDAO.getInstance().getDatas().count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("CocktailCell") as  UITableViewCell
        
        let cocktail = CocktailDAO.getInstance().getDatas()[indexPath.row]
        cell.textLabel.text = cocktail.name
        
        return cell
    }
    
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            CocktailDAO.getInstance().deleteData(CocktailDAO.getInstance().getDatas()[indexPath.row])
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "ToDetailCocktail"){
            let svc = segue.destinationViewController as DetailCocktailViewController
            let index = tableView.indexPathForCell(sender as UITableViewCell)!
            svc.cocktail = CocktailDAO.getInstance().getDatas()[index.row]
        } else if(segue.identifier == "ToAddCocktail"){
            let svc = segue.destinationViewController as AddCocktailViewController
            svc.completionHandler = { (result : CocktailDAO.Result) -> () in
                var alert : UIAlertView = UIAlertView(title: "Results", message: "", delegate: nil, cancelButtonTitle: "Ok")
                switch(result){
                case .Saved:
                    alert.message = "Data saved"
                    self.tableView.reloadData()
                case .Cancel:
                    alert.message = "One or even field is empty"
                }
                alert.show()
            }
        }
    }
    
    @IBAction func tmpSave(sender: AnyObject) {
        CocktailDAO.getInstance().saveData()
    }
}

