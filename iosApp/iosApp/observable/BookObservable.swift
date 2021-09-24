//
//  BookObservableVM.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

final class BookObservable: ObservableObject {
    let bookVM: BookViewModel
    @Published private(set) var state: LoadMoreState<Book>
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<BookEffect, Never>()

    init(viewModel: BookViewModel) {
        KoinKt.log.d(withMessage: {"BookObservable init viewmodel"})
        self.bookVM = viewModel
        self.state = LoadMoreState<Book>()
    }
    
    deinit {
        KoinKt.log.d(withMessage: {"BookObservable deinit"})
        bookVM.onCleared()
    }
    
    func startObserving() {
        KoinKt.log.d(withMessage: {"BookObservable startObserving"})
        stateCloseable = bookVM.observe(bookVM.state, onChange: {
            self.state = $0 as! LoadMoreState<Book>
        })
        effectCloseable = bookVM.observe(bookVM.effect, onChange: {
            self.effect.send($0 as! BookEffect)
        })
    }
    
    func stopObserving() {
        KoinKt.log.d(withMessage: {"BookObservable stopObserving"})
        stateCloseable.close()
        effectCloseable.close()
    }
}
