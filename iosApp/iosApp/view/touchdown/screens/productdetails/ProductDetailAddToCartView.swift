//
//  ProductDetailAddToCartView.swift
//  Coca
//
//  Created by Huynh Phong on 05/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailAddToCartView: View {

  @EnvironmentObject var shop: Shop

  var body: some View {
    Button {
      feedback.impactOccurred()
    } label: {
      Spacer()
      Text("Add to cart".uppercased())
        .font(.system(.title2, design: .rounded))
        .fontWeight(.bold)
        .foregroundColor(.white)
      Spacer()
    }
      .padding(16)
      .background(shop.selectedProducted?.colorValue ?? sampleProduct.colorValue)
      .clipShape(Capsule())
  }
}

struct ProductDetailAddToCartView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailAddToCartView()
      .environmentObject(Shop())
      .previewLayout(.sizeThatFits)
      .padding()
  }
}
