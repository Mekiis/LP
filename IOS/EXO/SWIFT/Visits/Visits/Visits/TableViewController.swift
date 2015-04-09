//
//  TableViewController.swift
//  Visites
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class TableViewController: UITableViewController, UITableViewDelegate, UITableViewDataSource {

    var locations : [Location]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.locations = LocationManager(coreDataManager: DataManager.SharedManager).loadLocation()
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
                
            }
        }
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
        if segue.identifier == "ChooseLocation"{
            
            let navCon = segue.destinationViewController as UINavigationController
            let clv = navCon.viewControllers[0] as ViewController
            
            clv.completionHandler = {() -> () in
                self.locations = LocationManager(coreDataManager: DataManager.SharedManager).loadLocation()
                self.tableView.reloadData()
                var alert : UIAlertView = UIAlertView(title: "Results", message: "Data saved", delegate: nil, cancelButtonTitle: "OK")
                alert.show()
            }
        } else if segue.identifier == "GoToSeePlaces"{
            let pts = segue.destinationViewController as PlacesToSeeViewController
            let indexPath = self.tableView.indexPathForSelectedRow()!
            pts.location = locations![indexPath.row]
            
        }
    }


}
