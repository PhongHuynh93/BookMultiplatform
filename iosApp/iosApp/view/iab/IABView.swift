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
    @StateObject var observable: IABObservable = koin.get()

    var body: some View {
        CocaWebView(url: URL(string: iabNav.url)!)
            .navigationBarTitle(Text(iabNav.title), displayMode: .inline)
            .onAppear { observable.startObserving() }
            .onDisappear { observable.stopObserving() }
    }
}
