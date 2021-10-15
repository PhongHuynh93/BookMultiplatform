//
//  GenreObservable.swift
//  Coca
//
//  Created by Huynh Phong on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Combine
import Foundation
import shared

final class GenreObservable: ObservableObject {
    let genreVM: GenreViewModel
    @Published private(set) var state: LoadingState
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<GenreEffect, Never>()
    let event: GenreEvent

    init(viewModel: GenreViewModel) {
        KoinKt.log.d(withMessage: { "GenreViewModel init viewmodel" })
        genreVM = viewModel
        state = LoadingState()
        event = viewModel.event
    }

    deinit {
        KoinKt.log.d(withMessage: { "GenreViewModel deinit" })
        genreVM.onCleared()
    }

    func startObserving() {
        KoinKt.log.d(withMessage: { "GenreViewModel startObserving" })
        stateCloseable = genreVM.observe(genreVM.state, onChange: {
            self.state = $0 as! LoadingState
        })
        effectCloseable = genreVM.observe(genreVM.genreEffect, onChange: {
            self.effect.send($0 as! GenreEffect)
        })
    }

    func stopObserving() {
        KoinKt.log.d(withMessage: { "GenreViewModel stopObserving" })
        stateCloseable.close()
        effectCloseable.close()
    }
}
