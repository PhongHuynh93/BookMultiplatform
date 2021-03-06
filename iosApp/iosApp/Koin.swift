//
//  Koin.swift
//  iosApp
//
//  Created by Huynh Phong on 19/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

func startKoin() {
    // You could just as easily define all these dependencies in Kotlin, but this helps demonstrate how you might pass platform-specific dependencies in a larger scale project where declaring them in Kotlin is more difficult, or where they're also used in iOS-specific code.

    let userDefaults = UserDefaults(suiteName: "BOOK_MULTIPLATFORM_SETTINGS")!
    let iosAppInfo = IosAppInfo()
    let doOnStartup = { NSLog("Hello from iOS/Swift!") }

    let koinApplication = KoinIOSKt.doInitKoinIos(userDefaults: userDefaults, appInfo: iosAppInfo, doOnStartup: doOnStartup)
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    return _koin!
}

class IosAppInfo: AppInfo {
    let appId: String = Bundle.main.bundleIdentifier!
}

// swiftlint:disable force_cast
extension Koin_coreKoin {
    // Viewmodel
    func get() -> BookViewModel {
        return koin.getDependency(objCClass: BookViewModel.self) as! BookViewModel
    }

    func get() -> IABViewModel {
        return koin.getDependency(objCClass: IABViewModel.self) as! IABViewModel
    }

    func get() -> GenreViewModel {
        return koin.getDependency(objCClass: GenreViewModel.self) as! GenreViewModel
    }

    func get() -> ArtistViewModel {
        return koin.getDependency(objCClass: ArtistViewModel.self) as! ArtistViewModel
    }

  func get() -> HomeViewModel {
      return koin.getDependency(objCClass: HomeViewModel.self) as! HomeViewModel
  }

    // Observable
    func get() -> BookObservable {
        return BookObservable(viewModel: get())
    }

    func get() -> IABObservable {
        return IABObservable(viewModel: get())
    }

    func get() -> GenreObservable {
        return GenreObservable(viewModel: get())
    }

    func get() -> ArtistObservable {
        return ArtistObservable(viewModel: get())
    }

  func get() -> WallupObservable {
      return WallupObservable(viewModel: get())
  }
}
