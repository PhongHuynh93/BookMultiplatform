//
//  BrandGridView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BrandGridView: View {

  var body: some View {
    ScrollView(.horizontal, showsIndicators: false) {
      LazyHGrid(rows: gridLayout, spacing: columnSpacing) {
        ForEach(brands) { brand in
          BrandItemView(brand: brand)
        } //: ForEach
      } //: LazyHGrid
      .frame(height: 200)
        .padding(16)
    } //: ScrollView
  }
}

struct BrandGridView_Previews: PreviewProvider {
  static var previews: some View {
    BrandGridView()
      .previewLayout(.sizeThatFits)
      .background(colorBackground)
  }
}

struct BrandItemView: View {

  let brand: Brand

  var body: some View {
    Image(brand.image)
      .resizable()
      .scaledToFit()
      .padding()
      .background(Color.white.cornerRadius(12))
      .background(RoundedRectangle(cornerRadius: 12).stroke(.gray, lineWidth: 1))
  }
}

struct BrandItemView_Previews: PreviewProvider {
  static var previews: some View {
    BrandItemView(brand: brands.first!)
      .previewLayout(.sizeThatFits)
      .padding()
      .background(colorBackground)
  }
}
