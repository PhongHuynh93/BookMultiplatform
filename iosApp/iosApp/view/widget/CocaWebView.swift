//
//  CocaWebView.swift
//  iosApp
//
//  Created by Huynh Phong on 25/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import WebKit

struct CocaWebView : UIViewRepresentable {
    
    let request: URLRequest
    
    func makeUIView(context: Context) -> WKWebView  {
        return WKWebView()
    }
    
    func updateUIView(_ uiView: WKWebView, context: Context) {
        uiView.load(request)
    }
    
}

struct WebView_Previews : PreviewProvider {
    static var previews: some View {
        CocaWebView(request: URLRequest(url: URL(string: "https://www.apple.com")!))
    }
}
