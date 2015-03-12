//
//  Cocktail.swift
//  Cocktail
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 iem. All rights reserved.
//

import Foundation

func == (lhs : Cocktail , rhs : Cocktail) -> Bool {
    return lhs.name == rhs.name
        && lhs.directions == rhs.directions
        && lhs.ingredients == rhs.ingredients
}

struct Cocktail : Equatable {
    let name, ingredients, directions : String
}
