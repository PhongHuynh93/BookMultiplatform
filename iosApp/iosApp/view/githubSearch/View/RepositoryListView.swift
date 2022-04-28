//
//  RepositoryListView.swift
//  GitHubSearchWithSwiftUI
//
//  Created by marty-suzuki on 2019/06/05.
//  Copyright © 2019 jp.marty-suzuki. All rights reserved.
//

import SwiftUI

struct RepositoryListView : View {

    @ObservedObject var viewModel: RepositoryListViewModel

    var body: some View {

        NavigationView {

            VStack {

                HStack {
                    TextField("Search reposipories...", text: $viewModel.text)
                        .frame(height: 40)
                        .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
//                        .border(Color.gray, cornerRadius: 8)
                        .padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))

                    Button<Text>(LocalizedStringKey("Search")) { self.viewModel.search() }
                        .frame(height: 40)
                        .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
//                        .border(Color.blue, cornerRadius: 8)
                        .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16))
                }

                List {
                    if viewModel.isLoading {
                        Text("Loading...")
                    } else {
                        viewModel.errorMessage.map(Text.init)?
                            .lineLimit(nil)
                            .multilineTextAlignment(.center)

                        ForEach(viewModel.repositories) { repository in

                            NavigationLink(destination:
                                WebView(url: repository.htmlUrl)
                                    .navigationBarTitle(Text(repository.fullName))
                            ) {

                                RepositoryView(repository: repository)
                            }
                        }
                    }
                }
                .navigationBarTitle(Text("Search🔍"))
            }
        }
    }
}
