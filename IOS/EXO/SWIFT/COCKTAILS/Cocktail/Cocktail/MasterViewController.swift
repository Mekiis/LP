//
//  ViewController.swift
//  Cocktail
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class MasterViewController: UITableViewController, UISearchBarDelegate, UISearchDisplayDelegate {

    var filteredCocktails = [Cocktail]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        CocktailDAO.getInstance().getDatas()
        self.title = "\u{1F498} Cocktails \u{1F498}"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == self.searchDisplayController!.searchResultsTableView {
            return self.filteredCocktails.count
        } else {
            return CocktailDAO.getInstance().getDatas().count
        }
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        //ask for a reusable cell from the tableview, the tableview will create a new one if it doesn't have any
        var cell : UITableViewCell
        
        var cocktail : Cocktail
        // Check to see whether the normal table or search results table is being displayed and set the Candy object from the appropriate array
        if tableView == self.searchDisplayController!.searchResultsTableView {
            cell = self.tableView.dequeueReusableCellWithIdentifier("SearchCocktailCell") as UITableViewCell
            cocktail = filteredCocktails[indexPath.row]
        } else {
            cell = self.tableView.dequeueReusableCellWithIdentifier("CocktailCell") as UITableViewCell
            cocktail = CocktailDAO.getInstance().getDatas()[indexPath.row]
        }
        
        // Configure the cell
        cell.textLabel.text = cocktail.name
        cell.detailTextLabel?.text = cocktail.ingredients
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        
        return cell
    }
    
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            if tableView == self.searchDisplayController!.searchResultsTableView {
                CocktailDAO.getInstance().deleteData(CocktailDAO.getInstance().getDatas()[CocktailDAO.getInstance().getIndexFor(filteredCocktails[indexPath.row])])
                filteredCocktails.removeAtIndex(indexPath.row)
            } else {
                CocktailDAO.getInstance().deleteData(CocktailDAO.getInstance().getDatas()[indexPath.row])

            }
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
        }
    }
    
    func searchBarCancelButtonClicked(searchBar: UISearchBar) {
        self.tableView.reloadData()
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        if searchText == ""{
            self.tableView.reloadData()
        }
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "ToDetailCocktail"){
            let svc = segue.destinationViewController as DetailCocktailViewController
            if (sender as UITableViewCell).reuseIdentifier == "SearchCocktailCell" {
                let indexPath = self.searchDisplayController!.searchResultsTableView.indexPathForSelectedRow()!
                svc.cocktail = filteredCocktails[indexPath.row]
            } else {
                let indexPath = self.tableView.indexPathForSelectedRow()!
                svc.cocktail = CocktailDAO.getInstance().getDatas()[indexPath.row]
            }
            svc.completionHandler = { (needReload : Bool) -> () in
                if(needReload == true){
                    self.tableView.reloadData()
                }
            }
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
    
    func filterContentForSearchText(searchText: String) {
        // Filter the array using the filter method
        self.filteredCocktails = CocktailDAO.getInstance().getDatas().filter({( cocktail: Cocktail) -> Bool in
            let stringMatch = cocktail.name.rangeOfString(searchText)
            return stringMatch != nil
        })
    }
    
    func searchDisplayController(controller: UISearchDisplayController!, shouldReloadTableForSearchString searchString: String!) -> Bool {
        self.filterContentForSearchText(searchString)
        return true
    }
    
    func searchDisplayController(controller: UISearchDisplayController!, shouldReloadTableForSearchScope searchOption: Int) -> Bool {
        self.filterContentForSearchText(self.searchDisplayController!.searchBar.text)
        return true
    }
}

