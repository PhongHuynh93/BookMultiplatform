//
//  BookView.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BookView: View {
    @StateObject var observable: BookObservable = koin.get()
    
    var body: some View {
        NavigationView {
//            List() { item in
//
//            }
            Text("Text get books")
            .navigationTitle("Book")
        }
    }
}

struct BookView_Previews: PreviewProvider {
    static var previews: some View {
        BookView()
    }
}
