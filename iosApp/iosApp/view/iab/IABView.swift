//
//  IABView.swift
//  iosApp
//
//  Created by Huynh Phong on 24/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared
import SwiftUI

struct IABView: View {
    @Binding var iabNav: IABNav

    var body: some View {
        CocaWebView(request: URLRequest(url: URL(string: iabNav.url)!))
            .navigationBarTitle(Text(iabNav.title), displayMode: .inline)
    }
}
