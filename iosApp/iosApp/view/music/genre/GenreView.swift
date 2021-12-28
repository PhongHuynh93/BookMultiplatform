//
//  GenreView.swift
//  Coca
//
//  Created by Huynh Phong on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Kingfisher
import shared
import SwiftUI

struct GenreView: View {
    @StateObject var observable: GenreObservable = koin.get()

    // make at least 2 columns
    let columns = [
        GridItem(.adaptive(minimum: 140), spacing: 10),
    ]

    var body: some View {
        VStack {
            switch observable.state.screen {
            case _ as LoadingScreen.Loading:
                LoadingView()
            case let noData as LoadingScreen.NoData:
                Text(noData.message)
            case let error as LoadingScreen.Error:
                Text(error.errorMessage)
            case let data as LoadingScreenData<Genre>:
                ScrollView {
                    LazyVGrid(columns: columns) {
                        ForEach(data.data as! [Genre], id: \.id) { genre in
                            NavigationLink(destination: ArtistView(genre: genre)) {
                                ZStack {
                                    GeometryReader { gr in
                                        KFImage(URL(string: genre.model.pictureMedium))
                                            .placeholder()
                                            .resizable()
                                            .scaledToFill()
                                            .frame(height: gr.size.height)
                                    }
                                    .aspectRatio(genreRatio, contentMode: .fill)
                                    .overlay(
                                        ZStack {
                                            Color("overlay")
                                            Text(genre.model.name)
                                                .font(.subheadline)
                                                .bold()
                                                .foregroundColor(Color.white)
                                        }
                                    )
                                }
                                .cornerRadius(smallRadius)
                                .clipped()
                                .onAppear {
                                    observable.loadMore(indexOfItem: (data.data as! [Genre]).firstIndex(of: genre)!)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
            default:
                Text("Not Handling")
            }
        }
        .navigationTitle("Genre")
        .onAppear { observable.startObserving() }
        .onDisappear { observable.stopObserving() }
        .onReceive(observable.effect) { onEffect(effect: $0) }
    }

    private func onEffect(effect: GenreEffect) {
        KoinKt.log.d(message: { "Effect \(effect)" })
        switch effect {
        case let lmEffect as GenreEffect.LoadMoreEffect:
            switch lmEffect.loadMoreEffect {
            case is LoadMoreEffect.ScrollToTop:
                KoinKt.log.d(message: { "Scroll to top" })
            default:
                KoinKt.log.d(message: { "Unknown effect" })
            }
        default:
            KoinKt.log.d(message: { "Unknown effect" })
        }
    }
}

struct GenreView_Previews: PreviewProvider {
    static var previews: some View {
        GenreView()
    }
}
