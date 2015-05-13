//
//  SiteTileView.swift
//  Visits
//
//  Created by iem on 07/05/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//
import QuartzCore
import UIKit

@IBDesignable
public class SiteTileView: UIView {
    var backgoundRingLayer : CAShapeLayer?
    var ringLayer : CAShapeLayer?
    var imageLayer: CALayer?
    
    var image: UIImage?{
        didSet{ updateLayerProperties()}
    }

    @IBInspectable var rating: CGFloat = 0.6{
        didSet{ updateLayerProperties()}
    }
    
    @IBInspectable var lineWidth: CGFloat = 10.0{
        didSet{ updateLayerProperties()}
    }
    
    override public func layoutSubviews() {
        super.layoutSubviews()
        
        if backgoundRingLayer == nil{
            backgoundRingLayer = CAShapeLayer()
            layer.addSublayer(backgoundRingLayer)
            
            let rect = CGRectInset(bounds, lineWidth/2.0, lineWidth/2.0)
            let path = UIBezierPath(ovalInRect: rect)
            
            backgoundRingLayer!.path = path.CGPath
            backgoundRingLayer!.fillColor = nil
            backgoundRingLayer!.lineWidth = lineWidth
            backgoundRingLayer!.strokeColor = UIColor(white: 0.5, alpha: 0.3).CGColor
        }
        backgoundRingLayer!.frame = layer.bounds
        
        if ringLayer == nil{
            ringLayer = CAShapeLayer()
            
            let innerRect = CGRectInset(bounds, lineWidth/2.0, lineWidth/2.0)
            let innerPath = UIBezierPath(ovalInRect: innerRect)
            
            ringLayer!.path = innerPath.CGPath
            ringLayer!.fillColor = nil
            ringLayer!.lineWidth = lineWidth
            ringLayer!.strokeColor = UIColor.blueColor().CGColor
            ringLayer!.anchorPoint = CGPointMake(0.5, 0.5)
            ringLayer!.transform = CATransform3DRotate(ringLayer!.transform, CGFloat(-M_PI)/2.0, 0, 0, 1)
            
            layer.addSublayer(ringLayer)
        }
        ringLayer!.frame = layer.bounds
        
        if imageLayer == nil {
            let imageMaskLayer = CAShapeLayer()
            
            let insetBounds = CGRectInset(bounds, lineWidth+3.0, lineWidth+3.0)
            let innerPath = UIBezierPath(ovalInRect: insetBounds)
            
            imageMaskLayer.path = innerPath.CGPath
            imageMaskLayer.fillColor = UIColor.blackColor().CGColor
            imageMaskLayer.frame = bounds
            layer.addSublayer(imageMaskLayer)
            
            imageLayer = CALayer()
            imageLayer!.mask = imageMaskLayer
            imageLayer!.frame = bounds
            imageLayer!.backgroundColor = UIColor.whiteColor().CGColor
            imageLayer!.contentsGravity = kCAGravityResizeAspectFill
            layer.addSublayer(imageLayer)
        }
        
        updateLayerProperties()
    }
    
    func updateLayerProperties() {
        if ringLayer != nil {
            ringLayer!.strokeEnd = rating
            
            var strokeColor = UIColor.lightGrayColor()
            
            switch rating{
            case let r where r >= 0.75:
                strokeColor = UIColor(hue: 112.0/360.0, saturation: 0.39, brightness: 0.85, alpha: 1.0)
            case let r where r >= 0.5:
                strokeColor = UIColor(hue: 208.0/360.0, saturation: 0.51, brightness: 0.75, alpha: 1.0)
            case let r where r >= 0.25:
                strokeColor = UIColor(hue: 40.0/360.0, saturation: 0.39, brightness: 0.85, alpha: 1.0)
            default:
                strokeColor = UIColor(hue: 359.0/360.0, saturation: 0.48, brightness: 0.63, alpha: 1.0)
            }
            ringLayer!.strokeColor = strokeColor.CGColor
        }
        
        if imageLayer != nil {
            if let i = image {
                imageLayer?.contents = i.CGImage
            }
        }
    }
    
    public func changeImage(newImage : UIImage){
        image = newImage
    }
    
    public func changeRating(newRating : CGFloat){
        rating = newRating
    }
    
}
