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
            GeometryReader { gr in
            Image({
              switch (category) {
              case WallupCategory.abstract: return "ic_abstract"
              case WallupCategory.animals: return "ic_animals"
              case WallupCategory.anime: return "ic_anime"
              case WallupCategory.art: return "ic_arts"
              case WallupCategory.cars: return "ic_cars"
              case WallupCategory.city: return "ic_city"
              case WallupCategory.dark: return "ic_dark"
              case WallupCategory.flowers: return "ic_flowers"
              case WallupCategory.food: return "ic_food"
              case WallupCategory.holidays: return "ic_holidays"
              case WallupCategory.love: return "ic_love"
              case WallupCategory.macro: return "ic_macro"
              case WallupCategory.motorcycles: return "ic_motorcycles"
              case WallupCategory.music: return "ic_music"
              case WallupCategory.nature: return "ic_nature"
              case WallupCategory.space: return "ic_space"
              case WallupCategory.sport: return "ic_sports"
              case WallupCategory.technologies: return "ic_tech"
              case WallupCategory.vector: return "ic_vector"
              case WallupCategory.words: return "ic_words"
              default: return "ic_words"
              }
            }())
              .resizable()
              .scaledToFill()
              .frame(height: gr.size.height)
              .clipShape(RoundedRectangle(cornerRadius: 10))
            }
            Text(category.title)
              .foregroundColor(Color.white)
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
