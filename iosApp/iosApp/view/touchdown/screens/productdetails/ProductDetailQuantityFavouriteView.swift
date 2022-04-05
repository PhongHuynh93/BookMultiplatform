//
//  ProductDetailQuantityFavouriteView.swift
//  Coca
//
//  Created by Huynh Phong on 05/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProductDetailQuantityFavouriteView: View {
  @State private var counter: Int = 0

  var body: some View {
    HStack(alignment: .center, spacing: 6) {
      Button {
        if counter > 0 {
          feedback.impactOccurred()
          counter -= 1
        }
      } label: {
        Image(systemName: "minus.circle")
      }

      Text(counter.description)
        .fontWeight(.semibold)
        .frame(minWidth: 36)

      Button {
        if counter < 100 {
          feedback.impactOccurred()
          counter += 1
        }
      } label: {
        Image(systemName: "plus.circle")
      }

      Spacer()

      Button {
        feedback.impactOccurred()
      } label: {
        Image(systemName: "heart.circle")
          .foregroundColor(.pink)
      }

    } //: HStack
    .font(.system(.title, design: .rounded))
      .foregroundColor(.black)
      .imageScale(.large)
  }
}

struct ProductDetailQuantityFavouriteView_Previews: PreviewProvider {
  static var previews: some View {
    ProductDetailQuantityFavouriteView()
      .previewLayout(.sizeThatFits)
      .padding()
  }
}
