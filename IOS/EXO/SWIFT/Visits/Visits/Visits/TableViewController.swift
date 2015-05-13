//
//  TableViewController.swift
//  Visites
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit
import MapKit

class TableViewController: UITableViewController, UITableViewDelegate, UITableViewDataSource {

    var locations : [Location]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.locations = LocationManager(coreDataManager: DataManager.SharedManager).loadLocation()
        locations!.sort({ $0.name.localizedCaseInsensitiveCompare($1.name) == NSComparisonResult.OrderedAscending })
        tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let locationsValues = locations{
            return locationsValues.count
        }
        return 0
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("location", forIndexPath: indexPath) as UITableViewCell
        
        if let locationsValues = locations{
            cell.textLabel.text = locationsValues[indexPath.row].name
        }
        
        return cell
    }

    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
   

    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        
        if editingStyle == .Delete {
            // Delete the row from the data source
            if let locationsValues = locations{
                LocationManager(coreDataManager: DataManager.SharedManager).deleteLocation(locationsValues[indexPath.row])
                self.locations?.removeAtIndex(indexPath.row)
                self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
                self.tableView.reloadData()
            }
        }
    }
    
    override func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        var editNotesAction = UITableViewRowAction(style: UITableViewRowActionStyle.Default, title: "Edit") { (action:UITableViewRowAction!, indexPath:NSIndexPath!) -> Void in
            
            //methods
            self.performSegueWithIdentifier("EditLocation", sender: tableView.cellForRowAtIndexPath(indexPath))
            
        }
        
        //change the background color of the swipe-to-edit
        editNotesAction.backgroundColor = UIColor(red: 49.0/255.0, green: 91.0/255.0, blue: 181.0/255.0, alpha: 1)
        
        var deleteNotesAction = UITableViewRowAction(style: UITableViewRowActionStyle.Default, title: "Delete") { (action:UITableViewRowAction!, indexPath:NSIndexPath!) -> Void in
            
            if let locationsValues = self.locations{
                LocationManager(coreDataManager: DataManager.SharedManager).deleteLocation(locationsValues[indexPath.row])
                self.locations?.removeAtIndex(indexPath.row)
                self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
            }

            
        }
        
        return [editNotesAction, deleteNotesAction]
    }


    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return NO if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "ChooseLocation" || segue.identifier == "EditLocation"{
            
            let navCon = segue.destinationViewController as UINavigationController
            let clv = navCon.viewControllers[0] as ViewController
            
            
            
            clv.action = Action.Add
            
            if segue.identifier == "EditLocation"{
                if let cell = sender as? UITableViewCell{
                    let indexPath = self.tableView.indexPathForCell(cell)!
                    
                    clv.pin = MKPointAnnotation()
                    clv.pin!.setCoordinate(CLLocationCoordinate2D(latitude : locations![indexPath.row].latitude, longitude : locations![indexPath.row].longitude))
                    clv.pin!.title = locations![indexPath.row].name
                    clv.oldLocation = locations![indexPath.row]
                    
                    clv.action = Action.Edit
                    clv.completionHandler = {() -> () in
                        //self.locations?.removeAtIndex(indexPath.row)
                        self.locations!.removeAll()
                        self.locations = LocationManager(coreDataManager: DataManager.SharedManager).loadLocation()
                        self.tableView.reloadData()
                    }
                }
            } else {
                clv.action = Action.Add
                clv.completionHandler = {() -> () in
                    self.locations = LocationManager(coreDataManager: DataManager.SharedManager).loadLocation()
                    self.locations!.sort({
                        $0.name.localizedCaseInsensitiveCompare($1.name) == NSComparisonResult.OrderedAscending
                    })
                    self.tableView.reloadData()
                }
            }
        } else if segue.identifier == "GoToSeePlaces"{
            let pts = segue.destinationViewController as PlacesToSeeViewController
            let indexPath = self.tableView.indexPathForSelectedRow()!
            pts.location = locations![indexPath.row]
            
        }
    }
    

}
