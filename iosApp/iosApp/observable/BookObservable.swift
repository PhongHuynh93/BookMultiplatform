//
//  BookObservableVM.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

final class BookObservable: ObservableObject {
    let bookVM: BookViewModel
//    @Published private(set) var data: [Book]
    
    init(viewModel: BookViewModel) {
        KoinKt.log.e(withMessage: {"BookObservable init viewmodel"})
        viewModel.observe(viewModel.data, onChange: { data in
//            KoinKt.log.e(withMessage: {"BookObservableVM \(String(describing: data)) "})
            // self.data = $0
        })
        self.bookVM = viewModel
    }
    
    deinit {
        KoinKt.log.e(withMessage: {"BookObservable deinit"})
        bookVM.onCleared()
    }
}
