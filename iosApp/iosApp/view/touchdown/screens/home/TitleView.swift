//
//  TitleView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TitleView: View {

  let title: String

  var body: some View {
      HStack {
          Text(title)
              .font(.largeTitle)
          .fontWeight(.heavy)

          Spacer()
      } //: HStack
      .padding(.horizontal)
      .padding(.top, 16)
      .padding(.bottom, 10)
  }
}

struct TitleView_Previews: PreviewProvider {
  static var previews: some View {
    TitleView(title: "Lorem")
      .previewLayout(.sizeThatFits)
      .background(colorBackground)
  }
}
