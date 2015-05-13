//
//  TypePlace.swift
//  Visits
//
//  Created by iem on 12/05/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation

class TypePlace{
    var name : String?
    var number : Int?
    var visited : Int?
    
    init(name: String, number: Int, visited: Int){
        self.name = name
        self.number = number
        self.visited = visited
    }
    
    func getRatioVisited() -> Float?{
        return Float(visited!) / Float(number!)
    }
}