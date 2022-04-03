//
//  ErrorView.swift
//  Coca
//
//  Created by Huynh Phong on 30/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ErrorView: View {
  let errorMessage: String
  let loadingEvent: LoadingEvent

  var body: some View {
    VStack {
      Text(errorMessage)
      Button("Retry") {
        loadingEvent.retry()
      }
    }
  }
}
