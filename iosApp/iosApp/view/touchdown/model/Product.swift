//
//  Product.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Product: Codable, Identifiable {
    let id: Int
    let name: String
    let image: String
    let price: Int
    let description: String
    let color: [Double]
}

extension Product {
    var formattedPrice: String {
        return "$\(price)"
    }
    var colorValue: Color {
        return Color(red: color[0], green: color[1], blue: color[2])
    }
}
