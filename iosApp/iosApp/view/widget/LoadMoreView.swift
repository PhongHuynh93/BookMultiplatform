//
//  LoadMoreView.swift
//  Coca
//
//  Created by Huynh Phong on 30/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared
import SwiftUI

struct LoadMoreView: View {
  let isEndPage: Bool
  let errorMessage: String?
  let loadMoreEvent: LoadMoreEvent

  var body: some View {
    VStack {
      // show the loading indicator
      // or show the error message and retry button
      if let message = errorMessage {
        ErrorView(
          errorMessage: message,
          loadMoreEvent: loadMoreEvent
        )
      } else if !isEndPage {
        LoadingView {
          loadMoreEvent.loadMore()
        }
      }
    }
  }
}
