//
//  HomeView.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        TabView {
            NavigationView {
                GenreView()
            }.tabItem {
                Label("Genre", systemImage: "music.note")
            }
            NavigationView {
                AudioBookView()
            }.tabItem {
                Label("AudioBook", systemImage: "airpodsmax")
            }
            NavigationView {
                MovieView()
            }.tabItem {
                Label("Movie", systemImage: "film")
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
