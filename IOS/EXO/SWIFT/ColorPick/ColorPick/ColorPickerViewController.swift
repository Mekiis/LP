//
//  ColorPickerViewController.swift
//  ColorPick
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

protocol ColorPickerDelegate {
    func userDidChoose(color : UIColor) -> ()
}

class ColorPickerViewController: UIViewController {
    
    var delegate : ColorPickerDelegate?
    var completionHanlder: ((UIColor) -> ())?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Click on color button
    
    @IBAction func GreenButtonClick(sender: AnyObject) {
        // MARK: - Green with delegate
        //delegate?.userDidChoose(UIColor (red:(26.0/255), green:(197.0/255), blue:(115.0/255), alpha:(1.0)))
        // MARK: - Green with Closure
        completionHanlder?(UIColor (red:(26.0/255), green:(197.0/255), blue:(115.0/255), alpha:(1.0)))
    }

    @IBAction func OrangeButtonClick(sender: AnyObject) {
        // MARK: - Orange with delegate
       //delegate?.userDidChoose(UIColor (red:(238.0/255), green:(102.0/255), blue:(6.0/255), alpha:(1.0)))
        // MARK: - Orange with Closure
        completionHanlder?(UIColor (red:(238.0/255), green:(102.0/255), blue:(6.0/255), alpha:(1.0)))
    }
    
    @IBAction func RedButtonClick(sender: AnyObject) {
        // MARK: - Red with delegate
        //delegate?.userDidChoose(UIColor (red:(110.0/255), green:(0.0/255), blue:(47.0/255), alpha:(1.0)))
        // MARK: - Red with Closure
        completionHanlder?(UIColor (red:(110.0/255), green:(0.0/255), blue:(47.0/255), alpha:(1.0)))
    }

}
