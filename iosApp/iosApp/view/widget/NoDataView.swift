//
//  NoDataView.swift
//  Coca
//
//  Created by Huynh Phong on 30/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct NoDataView: View {
  let message: String

  var body: some View {
    Text(message)
  }
}

struct NoDataView_Previews: PreviewProvider {
  static var previews: some View {
    NoDataView(message: "No data")
  }
}
