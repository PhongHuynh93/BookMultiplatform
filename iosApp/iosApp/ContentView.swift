import shared
import SwiftUI
import Kingfisher

struct ContentView: View {
  var body: some View {
    WallupHomeView()
  }
}

struct WallupHomeView: View {

  @StateObject var observable: WallupObservable = koin.get()

  var body: some View {
    NavigationView {
      VStack {
        switch observable.state.screen {
        case _ as LoadingScreen.Loading:
          LoadingView { }
        case let noData as LoadingScreen.NoData:
          NoDataView(message: noData.message)
        case let error as LoadingScreen.Error:
          ErrorView(
            errorMessage: error.errorMessage,
            loadingEvent: observable.event
          )
        case let data as LoadingScreenData<Home>:
          ScrollView {
            VStack {
              WelcomeSection()
              SuggestedSection(data: (data.data[2] as! Home.RandomPhotoList).randomPhotoList)
                .padding(.top)
              ColorSection(data: (data.data[0] as! Home.ColorList).colorList)
                .padding(.top)
              CategoriesSection(data: (data.data[1] as! Home.CategoryList).wallupCategoryList)
                .padding(.top)
            }
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
  let data: [UnsplashPhoto]

  var body: some View {
    VStack(alignment: .leading) {
      SectionTitle(title: "Suggested for you")
        .padding(.leading)
      ScrollView(.horizontal, showsIndicators: false) {
        LazyHStack {
          Spacer()
          ForEach(data, id: \.id) { unsplashPhoto in
            KFImage(URL(string: unsplashPhoto.smallImage))
              .placeholder()
              .resizable()
              .scaledToFill()
              .frame(width: 150)
              .clipShape(RoundedRectangle(cornerRadius: 10))
          }
          Spacer()
        }.frame(height: 250)
      }
    }
  }
}

struct SectionTitle: View {
  let title: String

  var body: some View {
    Text(title)
      .font(.headline)
  }
}

struct ColorSection: View {
  let data: [ColorItem]

  var body: some View {
    VStack(alignment: .leading) {
      SectionTitle(title: "The color tone")
        .padding(.leading)
      ScrollView(.horizontal, showsIndicators: false) {
        LazyHStack {
          Spacer()
          ForEach(data, id: \.hexCode) { colorItem in
            Color(hex: colorItem.hexCode)
              .frame(width: 50)
          }
          Spacer()
        }.frame(height: 50)
      }
    }
  }
}

struct CategoriesSection: View {
  let data: [WallupCategory]

  var body: some View {
    let columns: [GridItem] =
      Array(repeating: .init(.flexible()), count: 2)
    VStack(alignment: .leading) {
      SectionTitle(title: "Categories")
        .padding(.leading)
      LazyVGrid(columns: columns) {
        ForEach(data, id: \.title) { category in
          ZStack {
            Text(category.title)
          }.frame(height: 85)
        }
      }
        .padding(.leading)
        .padding(.trailing)
    }
  }
}

struct WallupHomeView_Previews: PreviewProvider {
  static var previews: some View {
    WallupHomeView()
  }
}
