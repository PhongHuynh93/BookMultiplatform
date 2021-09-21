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
    @Published private(set) var data: [Book] = []
    
    init(viewModel: BookViewModel) {
        KoinKt.log.e(withMessage: {"BookObservable init viewmodel"})
        self.bookVM = viewModel
        viewModel.observe(viewModel.data, onChange: {
            self.data = $0 as! [Book]
        })
    }
    
    deinit {
        KoinKt.log.e(withMessage: {"BookObservable deinit"})
        bookVM.onCleared()
    }
}
