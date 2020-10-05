//
//  SimpleMap_SwiftUIApp.swift
//  SimpleMap_SwiftUI
//
//  Created by Petr Pokorny on 10/5/20.
//

import SwiftUI

@main
struct SimpleMap_SwiftUIApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

extension UIApplication {
    static var mapTilerKey: String? {
        return Bundle.main.object(forInfoDictionaryKey: "MapTilerKey") as? String
    }
}
