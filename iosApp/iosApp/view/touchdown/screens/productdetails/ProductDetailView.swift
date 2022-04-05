//
//  ProductDetailView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailView: View {

  @EnvironmentObject var shop: Shop

  var body: some View {
    VStack(alignment: .leading, spacing: 6) {
      ProductDetailNavigationBarView()
        .padding(.horizontal)
        .padding(.top, (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.windows.first?.safeAreaInsets.top)

      ProductDetailHeaderView()
        .padding(.horizontal)

      ProductDetailTopPartView()
        .padding(.horizontal)
        .zIndex(1)

      VStack(alignment: .center, spacing: 0) {
        ProductDetailRatingsSizesView()
          .padding(.top, -20)
          .padding(.bottom, 10)

        ScrollView(.vertical, showsIndicators: false) {
          Text(shop.selectedProducted?.description ?? sampleProduct.description)
            .font(.system(.body, design: .rounded))
            .foregroundColor(.gray)
            .multilineTextAlignment(.leading)
        } //: ScrollView

        ProductDetailQuantityFavouriteView()
          .padding(.vertical, 10)

        ProductDetailAddToCartView()
          .padding(.bottom, 20)
      } //: VStack
      .padding(.horizontal)
        .background(
        Color.white
          .clipShape(CustomShape())
          .padding(.top, -105)
      )


    } //: VStack
    .zIndex(0)
      .ignoresSafeArea(.all, edges: .all)
      .background(
      (shop.selectedProducted?.colorValue ?? sampleProduct.colorValue)
        .ignoresSafeArea(.all, edges: .all))
  }
}

struct ProductDetailView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailView()
      .environmentObject(Shop()) 
  }
}
