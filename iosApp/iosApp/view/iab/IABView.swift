//
//  IABView.swift
//  iosApp
//
//  Created by Huynh Phong on 24/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct IABView: View {
    @Binding var iabNav: IABNav
    
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
            .navigationBarTitle(Text(iabNav.title), displayMode: .inline)
    }
}
