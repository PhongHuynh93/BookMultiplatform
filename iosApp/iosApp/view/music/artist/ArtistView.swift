//
//  ArtistView.swift
//  Coca
//
//  Created by Huynh Phong on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Kingfisher
import shared
import SwiftUI

struct ArtistView: View {
  @StateObject var observable: ArtistObservable = koin.get()

  private let genre: Genre

  // make at least 2 columns
  let columns = [
    GridItem(.adaptive(minimum: 140), spacing: 24)
  ]

  init(genre: Genre) {
    self.genre = genre
  }

  var body: some View {
    VStack {
      switch observable.state.screen {
      case _ as LoadingScreen.Loading:
        LoadingView {
          observable.event.setGenreId(genreId: genre.id)
        }
      case let noData as LoadingScreen.NoData:
        NoDataView(message: noData.message)
      case let error as LoadingScreen.Error:
        ErrorView(
          errorMessage: error.errorMessage,
          loadMoreEvent: observable.event
        )
      case let data as LoadingScreenData<Artist>:
        ScrollView {
          LazyVGrid(columns: columns) {
            ForEach(data.data as! [Artist], id: \.id) { artist in
              NavigationLink(destination: ArtistDetailView()) {
                ArtistItemView(artist: artist)
              }
            }
            // show the loading indicator or error message
            LoadMoreView(
              isEndPage: data.isEndPage,
              errorMessage: data.errorMessage,
              loadMoreEvent: observable.event
            )
          }
          .padding(.horizontal)
          .padding(.bottom)
        }
      default:
        Text("Not Handling")
      }
    }
    .onAppear { observable.startObserving() }
    .onDisappear { observable.stopObserving() }
    .onReceive(observable.effect) { onEffect(effect: $0) }
    .navigationTitle(genre.model.name)
  }

  private func onEffect(effect: ArtistEffect) {
    KoinKt.log.d(message: { "Effect \(effect)" })
    switch effect {
    default:
      KoinKt.log.d(message: { "Unknown effect" })
    }
  }
}

struct ArtistItemView: View {
  let artist: Artist

  var body: some View {
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
        .font(.body)
    }
    .padding(.bottom)
  }
}

struct ArtistItemItem_Previews: PreviewProvider {
  static var previews: some View {
    ArtistItemView(artist: FakeData.shared.artist)
      .previewLayout(.fixed(width: 200, height: 250))
  }
}

struct ArtistItemItem_Dark_Previews: PreviewProvider {
  static var previews: some View {
    ArtistItemView(artist: FakeData.shared.artist)
      .preferredColorScheme(.dark)
      .previewLayout(.fixed(width: 200, height: 250))
  }
}

