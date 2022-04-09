//
//  Constant.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

let players: [Player] = Decoder.decode("player.json")
let brands: [Brand] = Decoder.decode("brand.json")
let categories: [Category] = Decoder.decode("category.json")
let products: [Product] = Decoder.decode("product.json")

let sampleProduct = products.first!

let colorBackground = Color("ColorBackground")

let colorGray = Color(UIColor.systemGray4)

let rowSpacing: CGFloat = 10
let columnSpacing: CGFloat = 10

var gridLayout: [GridItem] {
    return Array(repeating: .init(.flexible(), spacing: rowSpacing), count: 2)
}

let feedback = UIImpactFeedbackGenerator(style: .medium)
