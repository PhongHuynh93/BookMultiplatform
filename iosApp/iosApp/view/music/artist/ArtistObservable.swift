//
//  ArtistObservable.swift
//  Coca
//
//  Created by Huynh Phong on 03/11/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Combine
import Foundation
import shared

final class ArtistObservable: ObservableObject {
    let artistVM: ArtistViewModel
    @Published private(set) var state: LoadingState
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<ArtistEffect, Never>()
    let event: ArtistEvent

    init(viewModel: ArtistViewModel) {
        KoinKt.log.d(withMessage: { "ArtistViewModel init viewmodel" })
        artistVM = viewModel
        state = LoadingState()
        event = viewModel.event
    }

    deinit {
        KoinKt.log.d(withMessage: { "ArtistViewModel deinit" })
        artistVM.onCleared()
    }

    func startObserving() {
        KoinKt.log.d(withMessage: { "ArtistViewModel startObserving" })
        stateCloseable = artistVM.observe(artistVM.state, onChange: {
            self.state = $0 as! LoadingState
        })
        effectCloseable = artistVM.observe(artistVM.artistEffect, onChange: {
            self.effect.send($0 as! ArtistEffect)
        })
    }

    func stopObserving() {
        KoinKt.log.d(withMessage: { "ArtistViewModel stopObserving" })
        stateCloseable.close()
        effectCloseable.close()
    }

    func loadMore(indexOfItem: Int) {
        artistVM.loadMore(indexOfItem: Int32(indexOfItem))
    }

    func setGenre(genre: Genre) {
        KoinKt.log.d(withMessage: { "setGenre \(genre)" })
        artistVM.genreId = genre.id
    }
}
