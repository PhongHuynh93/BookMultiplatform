//
//  ToolbarView.swift
//  Coca
//
//  Created by Huynh Phong on 18/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct ToolbarView: View {
    let title: String
    var backEvent: () -> Void

    var body: some View {
        HStack {
            ToolbarButton(clickEvent: backEvent, imgName: "chevron.left")

            Text(title)
                .font(.title3)

            Spacer()
        }.padding(EdgeInsets(top: 20, leading: 10, bottom: 5, trailing: 20))
    }
}

struct ToolbarButton: View {
    @Environment(\.colorScheme) var colorScheme

    var clickEvent: () -> Void
    var imgName: String

    var body: some View {
        Button(
            action: clickEvent,
            label: {
                Image(systemName: imgName)
                    .imageScale(.large)
                    .padding(.leading, 10)
            }
        ).padding(.trailing, 10)
    }
}

struct ToolbarView_Previews: PreviewProvider {
    static var previews: some View {
        ToolbarView(title: "Title") {
            
        }
    }
}
