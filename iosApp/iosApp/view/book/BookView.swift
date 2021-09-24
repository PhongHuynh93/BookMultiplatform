//
//  BookView.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import Kingfisher

struct BookView: View {
    @StateObject var observable: BookObservable = koin.get()
    
    var body: some View {
        NavigationView {
            List(observable.state.data as! [Book], id: \.id) { book in
                HStack(alignment: .top) {
                    KFImage(URL(string: book.thumb.url))
                        .placeholder {
                            VStack {
                                Color.gray
                            }
                        }
                        .resizable()
                        .aspectRatio(bookRatio, contentMode:.fill)
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
                        }.buttonStyle(.bordered)
                    }
                }.padding(EdgeInsets(top: 4, leading: 0, bottom: 4, trailing: 0))
            }
            .listStyle(.plain)
            .navigationTitle("Book")
        }
        .onAppear{ observable.startObserving() }
        .onDisappear{ observable.stopObserving() }
        .onReceive(observable.effect) { onEffect(effect: $0) }
    }
    
    private func onEffect(effect: LoadMoreEffect) {
        KoinKt.log.d(withMessage: {"Effect \(effect)"})
        switch effect {
        case is LoadMoreEffect.ScrollToTop:
            KoinKt.log.d(withMessage: {"Scroll to top"})
        default:
            KoinKt.log.d(withMessage: {"Unknown effect"})
        }
    }
}

struct BookView_Previews: PreviewProvider {
    static var previews: some View {
        BookView()
    }
}
