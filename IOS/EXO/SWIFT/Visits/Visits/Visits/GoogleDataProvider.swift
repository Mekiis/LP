//
//  GoogleDataProvider.swift
//  Feed Me
//
//  Created by Ron Kliffer on 8/30/14.
//  Copyright (c) 2014 Ron Kliffer. All rights reserved.
//

import UIKit
import Foundation
import CoreLocation

class GoogleDataProvider {
  
  let apiKey = "AIzaSyDrTvMEsK4qBvfwFbUAXuhfG5cH522Z0x4"
  var photoCache = [String:UIImage]()
  var placesTask = NSURLSessionDataTask()
  var session: NSURLSession {
    return NSURLSession.sharedSession()
  }
  
  func fetchPlacesNearCoordinate(coordinate: CLLocationCoordinate2D, radius: Double, types:[String], completion: (([Place]) -> Void)) -> ()
  {
    var urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=\(apiKey)&location=\(coordinate.latitude),\(coordinate.longitude)&radius=\(radius)&rankby=prominence&sensor=true"
    let typesString = types.count > 0 ? join("|", types) : "food"
    urlString += "&types=\(typesString)"
    urlString = urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
    
    if placesTask.taskIdentifier > 0 && placesTask.state == .Running {
      placesTask.cancel()
    }
    UIApplication.sharedApplication().networkActivityIndicatorVisible = true
    placesTask = session.dataTaskWithURL(NSURL(string: urlString)!) {data, response, error in
      UIApplication.sharedApplication().networkActivityIndicatorVisible = false
      var placesArray = [Place]()
      if let json = NSJSONSerialization.JSONObjectWithData(data, options:nil, error:nil) as? NSDictionary {
        println("\(json)")
        if let status = json["status"] as? String{
            println("Request status : \(status)")
        }
        if let results = json["results"] as? NSArray {
          for rawPlace:AnyObject in results {
            
            var place = PlaceManager(coreDataManager: DataManager.SharedManager).getPlaceById(rawPlace["place_id"] as String)
            if place == nil {
                place = PlaceManager(coreDataManager: DataManager.SharedManager).createPlace(rawPlace as NSDictionary)
            }
            
            placesArray.append(place!)
          }
        }
      }
      dispatch_async(dispatch_get_main_queue()) {
        completion(placesArray)
      }
    }
    placesTask.resume()
  }
  
  
  func fetchPhotoFromReference(reference: String, completion: ((UIImage?) -> Void)) -> ()
  {
    if let photo = photoCache[reference] as UIImage! {
      completion(photo)
    } else {
      let urlString = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=\(reference)&key=\(apiKey)"
      
      UIApplication.sharedApplication().networkActivityIndicatorVisible = true
      session.downloadTaskWithURL(NSURL(string: urlString)!) {url, response, error in
        UIApplication.sharedApplication().networkActivityIndicatorVisible = false
        let downloadedPhoto = UIImage(data: NSData(contentsOfURL: url)!)
        self.photoCache[reference] = downloadedPhoto
        dispatch_async(dispatch_get_main_queue()) {
          completion(downloadedPhoto)
        }
      }.resume()
    }
  }
}
