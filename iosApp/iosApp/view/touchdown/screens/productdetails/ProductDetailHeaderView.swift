//
//  ProductDetailHeaderView.swift
//  Coca
//
//  Created by Huynh Phong on 05/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailHeaderView: View {

  @EnvironmentObject var shop: Shop

  var body: some View {
    VStack(alignment: .leading, spacing: 6) {
      Text("Protective Gear")

      Text(shop.selectedProducted?.name ?? sampleProduct.name)
        .font(.largeTitle)
        .fontWeight(.black)
    }
      .foregroundColor(.white)
  }
}

struct ProductDetailHeaderView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailHeaderView()
      .environmentObject(Shop())
      .previewLayout(.sizeThatFits)
      .padding()
      .background(Color.gray)
  }
}
