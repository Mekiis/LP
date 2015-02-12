//
//  ViewController.swift
//  ColorPick
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class ViewController: UIViewController, ColorPickerDelegate, UIAlertViewDelegate {
    
    var previousColor : UIColor!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func userDidChoose(color: UIColor) {
        previousColor = self.view.backgroundColor
        self.view.backgroundColor = color
        self.dismissViewControllerAnimated(true, completion: nil)
        let alertView = UIAlertView(title: "New color", message: "Do you like the color ?", delegate: self, cancelButtonTitle: "No", otherButtonTitles: "Yes")
        
        alertView.show()
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        if(buttonIndex == alertView.cancelButtonIndex){
            self.view.backgroundColor = previousColor
        }
    }
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "PickColor"{
            let colorPickerViewController = segue.destinationViewController as ColorPickerViewController
            // MARK: - With a delegate
            // colorPickerViewController.delegate = self
            // MARK: - With a Closure
            colorPickerViewController.completionHanlder = { (color : UIColor) -> () in
                self.userDidChoose(color)
            }
        }
    }


}

