import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
      WallupHomeView()
    }
}

struct WallupHomeView: View {

  @StateObject var observable: WallupObservable = koin.get()

    var body: some View {
      VStack {
        switch observable.state.screen {
        case _ as LoadingScreen.Loading:
          LoadingView {}
        case let noData as LoadingScreen.NoData:
          NoDataView(message: noData.message)
        case let error as LoadingScreen.Error:
          ErrorView(
            errorMessage: error.errorMessage,
            loadingEvent: observable.event
          )
        case let data as LoadingScreenData<Home>:
          let suggestedSection = (data.data[2] as! Home.RandomPhotoList).randomPhotoList
          LazyVStack {
            WelcomeSection()
            SuggestedSection()
            ColorSection()
            CategoriesSection()
          }
        default:
          Text("Not Handling")
        }
      }
      .navigationTitle("Welcome to WallUp")
      .onAppear { observable.startObserving() }
      .onDisappear { observable.stopObserving() }
    }
}

struct WelcomeSection: View {
  var body: some View {
    VStack {
      Text("Discover Unsplash photos and find best wallpaper for you.")
        .font(.body)
    }
  }
}

struct SuggestedSection: View {
  var body: some View {
    Text("Not Handling")
  }
}

struct ColorSection: View {
  var body: some View {
    Text("Not Handling")
  }
}

struct CategoriesSection: View {
  var body: some View {
    Text("Not Handling")
  }
}

struct WallupHomeView_Previews: PreviewProvider {
    static var previews: some View {
        WallupHomeView()
    }
}
