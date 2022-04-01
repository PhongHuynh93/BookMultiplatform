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
        LoadingView {}
      case let noData as LoadingScreen.NoData:
        NoDataView(message: noData.message)
      case let error as LoadingScreen.Error:
        ErrorView(
          errorMessage: error.errorMessage,
          loadMoreEvent: observable.event
        )
      case let data as LoadingScreenData<Genre>:
        ScrollView {
          LazyVGrid(columns: columns) {
            ForEach(data.data as! [Genre], id: \.id) { genre in
              NavigationLink(destination: ArtistView(genre: genre)) {
                GenreItemView(genre: genre)
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
    .navigationTitle("Genre")
    .onAppear { observable.startObserving() }
    .onDisappear { observable.stopObserving() }
    .onReceive(observable.effect) { onEffect(effect: $0) }
  }

  private func onEffect(effect: GenreEffect) {
    KoinKt.log.d(message: { "Effect \(effect)" })
  }
}

struct GenreItemView: View {
  let genre: Genre

  var body: some View {
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
  }
}

struct GenreViewItem_Previews: PreviewProvider {
  static var previews: some View {
    GenreItemView(genre: FakeData.shared.genre)
      .previewLayout(.fixed(width: 200, height: 100))
  }
}

struct GenreViewItem_Dark_Previews: PreviewProvider {
  static var previews: some View {
    GenreItemView(genre: FakeData.shared.genre)
      .previewLayout(.fixed(width: 200, height: 100))
      .preferredColorScheme(.dark)
  }
}
