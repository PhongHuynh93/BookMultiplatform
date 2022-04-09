//
//  ProductDetailTopPartView.swift
//  Coca
//
//  Created by Huynh Phong on 05/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailTopPartView: View {

  @EnvironmentObject var shop: Shop

  @State private var isAnimating: Bool = false

  var body: some View {
    HStack(alignment: .center, spacing: 6) {
      VStack(alignment: .leading, spacing: 6) {
        Text("Price")
          .fontWeight(.semibold)

        Text(shop.selectedProducted?.formattedPrice ?? sampleProduct.formattedPrice)
          .font(.largeTitle)
          .fontWeight(.black)
          .scaleEffect(1.35, anchor: .leading)
      } //: VStack
      .offset(y: isAnimating ? -50 : -75)

      Spacer()

      Image(shop.selectedProducted?.image ?? sampleProduct.image)
        .resizable()
        .scaledToFit()
        .offset(y: isAnimating ? 0 : -35)
    } //: HStack
    .onAppear {
      withAnimation(.easeOut(duration: 0.75)) {
        isAnimating.toggle()
      }
    }
  }
}

struct ProductDetailTopPartView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailTopPartView()
      .environmentObject(Shop())
      .previewLayout(.sizeThatFits)
      .padding()
  }
}
