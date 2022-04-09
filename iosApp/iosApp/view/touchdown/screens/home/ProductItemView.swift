//
//  ProductItemView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductItemView: View {
  let product: Product

  var body: some View {
    VStack(alignment: .leading, spacing: 6) {
      Image(product.image)
        .resizable()
        .scaledToFit()
        .padding(10)
        .background(product.colorValue)
        .cornerRadius(12)

      Text(product.name)
        .font(.title3)
        .fontWeight(.black)

      Text(product.formattedPrice)
        .fontWeight(.semibold)
        .foregroundColor(.gray)

    } //: VStack
  }
}

struct ProductItemView_Previews: PreviewProvider {
  static var previews: some View {
    ProductItemView(product: products.first!)
      .previewLayout(.fixed(width: 200, height: 300))
      .padding()
      .background(colorBackground)
  }
}
