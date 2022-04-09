//
//  ProductDetailNavigationBarView.swift
//  Coca
//
//  Created by Huynh Phong on 05/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailNavigationBarView: View {
  @EnvironmentObject var shop: Shop

  var body: some View {
    HStack {
      Button {
        withAnimation(.easeIn) {
          feedback.impactOccurred()
          shop.selectedProducted = nil
          shop.showingProduct = false
        }
      } label: {
        Image(systemName: "chevron.left")
          .font(.title)
          .foregroundColor(.white)
      }

      Spacer()

      Button {
        //
      } label: {
        Image(systemName: "cart")
          .font(.title)
          .foregroundColor(.white)
      }

    }
  }
}

struct ProductDetailNavigationBarView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailNavigationBarView()
      .environmentObject(Shop())
      .previewLayout(.sizeThatFits)
      .padding() 
      .background(Color.gray)
  }
}
