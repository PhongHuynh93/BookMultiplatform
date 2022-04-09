//
//  Shop.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class Shop: ObservableObject {
    @Published var showingProduct: Bool = false
    @Published var selectedProducted: Product? = nil
}
