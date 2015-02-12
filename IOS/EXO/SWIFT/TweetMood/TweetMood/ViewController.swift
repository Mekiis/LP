//
//  ViewController.swift
//  TweetMood
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit
import Social

class ViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {

    @IBOutlet weak var text: UITextField!
    @IBOutlet weak var picker: UIPickerView!
    @IBOutlet weak var textHashtag: UITextField!

    let emoji  = [";)", ":)", ":(", ":O", "8)", ":o", ":D", "mdr", "lol"]
    let words = ["dors", "mange", "suis en cours", "galère", "cours", "poireaute"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func dismissKeyBoard(){
        self.view.endEditing(true)
    }

    @IBAction func tapBackground(sender: AnyObject) {
        dismissKeyBoard()
    }
    
    @IBAction func tweetIt(sender: AnyObject) {
        let strFinal = buildString()
        
        let tweetSheet = SLComposeViewController(forServiceType: SLServiceTypeTwitter)
        tweetSheet.setInitialText(strFinal)
        tweetSheet.completionHandler = { (result : SLComposeViewControllerResult) -> () in
            var alert : UIAlertView = UIAlertView(title: "Tweet result", message: "", delegate: nil, cancelButtonTitle: "Ok")
            switch result{
            case .Done:
                alert.message = "Tweet posted"
                break;
            case .Cancelled:
                alert.message = "Tweet aborded"
                break;
            }
            alert.show()
        }
        self.presentViewController(tweetSheet, animated: true, completion: nil)
    }

    @IBAction func postItOnFacebook(sender: AnyObject) {
        let strFinal = buildString()
        
        let tweetSheet = SLComposeViewController(forServiceType: SLServiceTypeFacebook)
        tweetSheet.setInitialText(strFinal)
        tweetSheet.completionHandler = { (result : SLComposeViewControllerResult) -> () in
            var alert : UIAlertView = UIAlertView(title: "Tweet result", message: "", delegate: nil, cancelButtonTitle: "Ok")
            switch result{
            case .Done:
                alert.message = "Post on facebook completed"
                break;
            case .Cancelled:
                alert.message = "Post on facebook aborded"
                break;
            }
            alert.show()
        }
        self.presentViewController(tweetSheet, animated: true, completion: nil)
    }
    
    func buildString() ->NSString{
        let strWord : String = words[picker.selectedRowInComponent(0)]
        let strEmojii : String = emoji[picker.selectedRowInComponent(1)]
        let strFinal : String = String(format: "%@ Je %@ %@ %@", text.text!, strWord, strEmojii, textHashtag.text!)
        
        return strFinal
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if (component == 0) {
            return words.count;
        } else if(component == 1){
            return emoji.count;
        }
        
        return 0;
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String! {
        if(component == 0){
            return words[row]
        } else if(component == 1){
            return emoji[row]
        }
        
        return "";
    }
}

