import NavigationStack
import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationStackView(
            transitionType: .default,
            easing: Animation.easeInOut(duration: 0.5)
        ) {
            HomeView()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
