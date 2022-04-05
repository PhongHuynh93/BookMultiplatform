//
//  HomeFooterView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HomeFooterView: View {
  var body: some View {
    VStack(alignment: .center, spacing: 10) {
      Text("We offer the most cutting edge, comfortable, lightweight and durable football helmets in the market at affordable prices.")
        .foregroundColor(.gray)
        .multilineTextAlignment(.center)

      Image("logo-lineal")
        .renderingMode(.template)
        .foregroundColor(.gray)

      Text("Copyright © Knot / knottx")
        .font(.footnote)
        .fontWeight(.bold)
        .foregroundColor(.gray)
        .multilineTextAlignment(.center)
    } //: VStack
    .padding()
  }
}

struct HomeFooterView_Previews: PreviewProvider {
  static var previews: some View {
    HomeFooterView()
      .previewLayout(.sizeThatFits)
      .background(colorBackground)
  }
}
