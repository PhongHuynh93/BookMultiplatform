import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        // Perform any tasks on app launch
        startKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
