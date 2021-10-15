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
    @State private var navIAB = false

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
                    LazyVGrid(columns: columns, spacing: 10) {
                        ForEach(data.data as! [Genre], id: \.id) { genre in
                            ZStack {
                                GeometryReader { gr in
                                    KFImage(URL(string: genre.model.pictureMedium))
                                        .placeholder {
                                            VStack {
                                                Color.gray
                                            }
                                        }
                                        .resizable()
                                        .scaledToFill()
                                        .frame(height: gr.size.height)
                                }
                                .aspectRatio(genreRatio, contentMode: .fill)
                                .cornerRadius(smallRadius)
                                .clipped()
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
                        }
                    }
                    .padding(.horizontal)
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
        KoinKt.log.d(withMessage: { "Effect \(effect)" })
        switch effect {
        case let lmEffect as GenreEffect.LoadMoreEffect:
            switch lmEffect.loadMoreEffect {
            case is LoadMoreEffect.ScrollToTop:
                KoinKt.log.d(withMessage: { "Scroll to top" })
            default:
                KoinKt.log.d(withMessage: { "Unknown effect" })
            }
        case is GenreEffect.NavToArtist:
            navIAB = true
        default:
            KoinKt.log.d(withMessage: { "Unknown effect" })
        }
    }
}

struct GenreView_Previews: PreviewProvider {
    static var previews: some View {
        GenreView()
    }
}
