//
//  BookObservableVM.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Combine
import Foundation
import shared

final class BookObservable: ObservableObject {
    let bookVM: BookViewModel
    @Published private(set) var state: LoadingState
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<BookEffect, Never>()
    let event: BookEvent

    init(viewModel: BookViewModel) {
        KoinKt.log.d(withMessage: { "BookObservable init viewmodel" })
        bookVM = viewModel
        state = LoadingState()
        event = viewModel.event
        bookVM.setBookName(bookName: "hardcover-fiction")
    }

    deinit {
        KoinKt.log.d(withMessage: { "BookObservable deinit" })
        bookVM.onCleared()
    }

    func startObserving() {
        KoinKt.log.d(withMessage: { "BookObservable startObserving" })
        stateCloseable = bookVM.observe(bookVM.state, onChange: {
            self.state = $0 as! LoadingState
        })
        effectCloseable = bookVM.observe(bookVM.bookEffect, onChange: {
            self.effect.send($0 as! BookEffect)
        })
    }

    func stopObserving() {
        KoinKt.log.d(withMessage: { "BookObservable stopObserving" })
        stateCloseable.close()
        effectCloseable.close()
    }
}
