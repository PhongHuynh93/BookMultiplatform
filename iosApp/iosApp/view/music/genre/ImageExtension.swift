//
//  ViewExtension.swift
//  Coca
//
//  Created by Huynh Phong on 16/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import Kingfisher
import SwiftUI

extension KFImage {
    func placeholder() -> KFImage {
        self.placeholder {
            VStack {
                Color.gray
            }
        }
    }
}
