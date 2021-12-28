//
//  ArtistView.swift
//  Coca
//
//  Created by Huynh Phong on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Kingfisher
import NavigationStack
import shared
import SwiftUI

struct ArtistView: View {
    @EnvironmentObject private var navigationStack: NavigationStack
    @StateObject var observable: ArtistObservable = koin.get()
    @State private var navToArtistDetail = false

    private let genre: Genre

    // make at least 2 columns
    let columns = [
        GridItem(.adaptive(minimum: 140), spacing: 10)
    ]

    init(genre: Genre) {
        self.genre = genre
    }

    var body: some View {
        ZStack {
            Color("bg").edgesIgnoringSafeArea(.all)
            VStack {
                ToolbarView(title: self.genre.model.name) {
                    navigationStack.pop()
                }
                VStack {
                    switch observable.state.screen {
                    case _ as LoadingScreen.Loading:
                        LoadingView()
                    case let noData as LoadingScreen.NoData:
                        Text(noData.message)
                    case let error as LoadingScreen.Error:
                        Text(error.errorMessage)
                    case let data as LoadingScreenData<Artist>:
                        ScrollView {
                            LazyVGrid(columns: columns, spacing: 10) {
                                ForEach(data.data as! [Artist], id: \.id) { artist in
                                    NavigationLink(destination: LazyView(ArtistDetailView()), isActive: self.$navToArtistDetail) {
                                        VStack {
                                            GeometryReader { gr in
                                                KFImage(URL(string: artist.model.pictureMedium))
                                                    .placeholder()
                                                    .resizable()
                                                    .scaledToFill()
                                                    .frame(height: gr.size.height)
                                            }
                                            .aspectRatio(1, contentMode: .fill)
                                            .clipShape(Circle())
                                            Text(artist.model.name)
                                        }
                                        .onAppear {
                                            observable.loadMore(indexOfItem: (data.data as! [Artist]).firstIndex(of: artist)!)
                                        }
                                    }
                                }
                            }
                            .padding(.horizontal)
                        }.navigationTitle("Artist")
                    default:
                        Text("Not Handling")
                    }
                }.frame(maxHeight: .infinity)

            }
            .onAppear {
                observable.startObserving()
                observable.event.setGenreId(genreId: genre.id)
            }
            .onDisappear { observable.stopObserving() }
            .onReceive(observable.effect) { onEffect(effect: $0) }
        }
    }

    private func onEffect(effect: ArtistEffect) {
        KoinKt.log.d(message: { "Effect \(effect)" })
        switch effect {
        default:
            KoinKt.log.d(message: { "Unknown effect" })
        }
    }
}
