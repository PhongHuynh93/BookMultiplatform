//
//  ObservableVM.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

// doi ten thanh bookvm
final class ObservableVM<ViewModel: BookViewModel>: ObservableObject {

    let viewModel: ViewModel

    init(viewModel: ViewModel) {
        KoinKt.log.e(withMessage: {"ObservableVM \(ViewModel.description()) init"})
        self.viewModel = viewModel
        
    }

    deinit {
        viewModel.onCleared()
    }
}
