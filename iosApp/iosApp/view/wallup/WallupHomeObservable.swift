//
//  WallupHomeObservable.swift
//  Coca
//
//  Created by Huynh Phong on 01/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

final class WallupObservable: ObservableObject {
  private let vm: HomeViewModel
  @Published private(set) var state: LoadingState
  private var stateCloseable: Ktor_ioCloseable!
  private var effectCloseable: Ktor_ioCloseable!
  let effect = PassthroughSubject<HomeEffect, Never>()
  let event: HomeEvent

  init(viewModel: HomeViewModel) {
      KoinKt.log.d(message: { "HomeViewModel init viewmodel" })
      vm = viewModel
      state = LoadingState()
      event = viewModel.event
  }

  func startObserving() {
      KoinKt.log.d(message: { "HomeViewModel startObserving" })
      stateCloseable = vm.observe(vm.state, onChange: {
          self.state = $0 as! LoadingState
      })
      effectCloseable = vm.observe(vm.effect, onChange: {
          self.effect.send($0 as! HomeEffect)
      })
  }

  func stopObserving() {
      KoinKt.log.d(message: { "HomeViewModel stopObserving" })
      stateCloseable.close()
      effectCloseable.close()
  }
}
