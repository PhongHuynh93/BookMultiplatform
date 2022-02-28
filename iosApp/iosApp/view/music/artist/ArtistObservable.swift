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
    private let artistVM: ArtistViewModel
    @Published private(set) var state: LoadingState
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<ArtistEffect, Never>()
    let event: ArtistEvent

    init(viewModel: ArtistViewModel) {
        KoinKt.log.d(message: { "ArtistViewModel init viewmodel" })
        artistVM = viewModel
        state = LoadingState()
        event = viewModel.event
    }

    deinit {
        KoinKt.log.d(message: { "ArtistViewModel deinit" })
    }

    func startObserving() {
        KoinKt.log.d(message: { "ArtistViewModel startObserving" })
        stateCloseable = artistVM.observe(artistVM.state, onChange: {
            self.state = $0 as! LoadingState
        })
        effectCloseable = artistVM.observe(artistVM.artistEffect, onChange: {
            self.effect.send($0 as! ArtistEffect)
        })
    }

    func stopObserving() {
        KoinKt.log.d(message: { "ArtistViewModel stopObserving" })
        stateCloseable.close()
        effectCloseable.close()
    }
}
