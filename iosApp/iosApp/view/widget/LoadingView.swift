//
//  LoadMoreView.swift
//  Coca
//
//  Created by Huynh Phong on 29/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
  var onAppear: () -> Void

  var body: some View {
    VStack {
      ProgressView()
    }
    .frame(width: 96, height: 96)
    .onAppear {
      onAppear()
    }
  }
}

struct LoadingIndicatorView_Previews: PreviewProvider {
  static var previews: some View {
    LoadingView {}
  }
}
