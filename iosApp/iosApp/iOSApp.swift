import shared
import SwiftUI

@main
struct iOSApp: App {
  init() {
    // Perform any tasks on app launch
    startKoin()
  }

  var body: some Scene {
    WindowGroup {
//            ContentView()
//      TouchDownHomeView()
//        .environmentObject(Shop())
      RepositoryListView(
        viewModel: RepositoryListViewModel(mainScheduler: DispatchQueue.main)
      )
    }
  }
}
