//
//  PlaceDetailViewController.swift
//  Visits
//
//  Created by iem on 09/04/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import UIKit

class PlaceDetailViewController: UIViewController, EDStarRatingProtocol {
    
    var place : Place!
    

    @IBOutlet weak var titleTextView: UITextView!
    @IBOutlet weak var detailsTextView: UITextView!
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var starRating: EDStarRating!
    let dataProvider = GoogleDataProvider()
    
    var completionHandler : (() -> ())?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        titleTextView.text = place.name
        detailsTextView.text = place.address
        
        starRating.starImage = UIImage(named: "star.png")
        starRating.starHighlightedImage = UIImage(named:"starhighlighted.png")
        starRating.maxRating = 5;
        starRating.delegate = self;
        starRating.horizontalMargin = 12;
        starRating.editable=true;
        starRating.displayMode = 0
        
        
        displayMark()
        
        if let photoData = place.picture as NSData? {
           image.image = UIImage(data: photoData)
        }else if place.photoReference != ""{
            dataProvider.fetchPhotoFromReference(place.photoReference){ imageData in
                self.image.image = imageData
                self.place.picture = UIImageJPEGRepresentation(imageData, 1);
                PlaceManager(coreDataManager: DataManager.SharedManager).updatePlace(self.place)
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func displayMark(){
        starRating.rating = Float(place.mark)
    }
    
    @IBAction func butttonClearRattingClick(sender: UIButton) {
        place.mark = 0
        PlaceManager(coreDataManager: DataManager.SharedManager).updatePlace(place)
        displayMark()
    }
    
    func starsSelectionChanged(control: EDStarRating!, rating: Float) {
        place.mark = Double(rating)
        PlaceManager(coreDataManager: DataManager.SharedManager).updatePlace(place)
        displayMark()
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
    }
    
    override func didMoveToParentViewController(parent: UIViewController?) {
        if parent == nil {
            completionHandler!()
        }
    }
    

}
