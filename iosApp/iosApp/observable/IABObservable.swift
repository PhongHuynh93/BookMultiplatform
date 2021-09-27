//
//  IABObservable.swift
//  iosApp
//
//  Created by Huynh Phong on 25/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Combine
import Foundation
import shared

final class IABObservable: ObservableObject {
    let vm: IABViewModel
    @Published private(set) var state: IABState
    private var stateCloseable: Ktor_ioCloseable!
    private var effectCloseable: Ktor_ioCloseable!
    let effect = PassthroughSubject<IABEffect, Never>()
    let event: IABEvent

    init(viewModel: IABViewModel) {
        KoinKt.log.d(withMessage: { "IABObservable init viewmodel" })
        vm = viewModel
        state = IABState()
        event = viewModel.event
    }

    deinit {
        KoinKt.log.d(withMessage: { "IABObservable deinit" })
        vm.onCleared()
    }

    func startObserving() {
        KoinKt.log.d(withMessage: { "IABObservable startObserving" })
        stateCloseable = vm.observe(vm.state, onChange: {
            self.state = $0 as! IABState
        })
        effectCloseable = vm.observe(vm.effect, onChange: {
            self.effect.send($0 as! IABEffect)
        })
    }

    func stopObserving() {
        KoinKt.log.d(withMessage: { "IABObservable stopObserving" })
        stateCloseable.close()
        effectCloseable.close()
    }
}