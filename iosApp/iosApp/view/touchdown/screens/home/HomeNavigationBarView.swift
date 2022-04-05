//
//  HomeNavigationBarView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HomeNavigationBarView: View {

  @State private var isAnimated: Bool = false

  var body: some View {
    HStack {
      Button {

      } label: {
        Image(systemName: "magnifyingglass")
          .font(.title)
          .foregroundColor(.black)
      } //: Button

      Spacer()

      LogoView()
        .opacity(isAnimated ? 1 : 0)
        .offset(x: 0, y: isAnimated ? 0 : -25)
        .onAppear {
        withAnimation(.easeOut(duration: 0.5)) {
          isAnimated.toggle()
        }
      }

      Spacer()

      Button {

      } label: {
        ZStack {
          Image(systemName: "cart")
            .font(.title)
            .foregroundColor(.black)

          Circle()
            .fill(Color.red)
            .frame(width: 14, height: 14, alignment: .center)
            .offset(x: 13, y: -10
          )
        }
      } //: Button

    } //: HStack
  }
}

struct HomeNavigationBarView_Previews: PreviewProvider {
  static var previews: some View {
    HomeNavigationBarView()
      .previewLayout(.sizeThatFits)
      .padding()
  }
}
