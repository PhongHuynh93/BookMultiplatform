//
//  BookView.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Kingfisher
import shared
import SwiftUI

struct BookView: View {
    @StateObject var observable: BookObservable = koin.get()
    @State private var navIAB = false
    @State private var iabNav = IABNav()

    var body: some View {
        VStack {
            switch observable.state.screen {
            case _ as LoadingScreen.Loading:
                LoadingView()
            case let noData as LoadingScreen.NoData:
                Text(noData.message)
            case let error as LoadingScreen.Error:
                Text(error.errorMessage)
            case let data as LoadingScreenData<Book>:
                NavigationLink(destination: LazyView(IABView(iabNav: self.$iabNav)), isActive: self.$navIAB) {}
                List(data.data as! [Book], id: \.id) { book in
                    HStack(alignment: .top) {
                        KFImage(URL(string: book.thumb.url))
                            .placeholder()
                            .resizable()
                            .aspectRatio(bookRatio, contentMode: .fill)
                            .frame(width: 128)
                            .cornerRadius(smallRadius)
                            .clipped()

                        VStack(alignment: .leading) {
                            Text(book.title)
                                .font(.headline)
                                .lineLimit(1)
                            Text(book.author)
                                .font(.caption)
                            Text(book.description_)
                                .padding(.top, 2)
                                .font(.body)
                                .lineLimit(3)
                            Spacer()
                            Button("Buy") {
                                KoinKt.log.d(withMessage: { "on tap buy" })
                                observable.event.onClickBuy(book: book)
                            }.buttonStyle(.bordered)
                        }
                    }.padding(EdgeInsets(top: 4, leading: 0, bottom: 4, trailing: 0))
                }.listStyle(.plain)
            default:
                Text("Not Handling")
            }
        }
        .navigationTitle("Book")
        .onAppear { observable.startObserving() }
        .onDisappear { observable.stopObserving() }
        .onReceive(observable.effect) { onEffect(effect: $0) }
    }

    private func onEffect(effect: BookEffect) {
        KoinKt.log.d(withMessage: { "Effect \(effect)" })
        switch effect {
        case let lmEffect as BookEffect.LMEffect:
            switch lmEffect.loadMoreEffect {
            case is LoadMoreEffect.ScrollToTop:
                KoinKt.log.d(withMessage: { "Scroll to top" })
            default:
                KoinKt.log.d(withMessage: { "Unknown effect" })
            }
        case let nav as BookEffect.NavToIAB:
            iabNav = nav.iabNav
            navIAB = true
        default:
            KoinKt.log.d(withMessage: { "Unknown effect" })
        }
    }
}

struct BookView_Previews: PreviewProvider {
    static var previews: some View {
        BookView()
    }
}
