//
//  SdkSample.swift
//  MapLibreExamples
//
//  Created by Petr Pokorny on 2/9/21.
//

import Foundation
import SwiftUI

struct SdkSample: Identifiable {
    let title: String
    let controller: AnyView
    let id: UUID

    init(title: String, controller: AnyView) {
        self.id = UUID()
        self.title = title
        self.controller = controller
    }
}

let sdkSamples = [
    SdkSample(title: "Basic Map", controller: AnyView(BasicMap())),
    SdkSample(title: "Annotations", controller:  AnyView(Annotations())),
    SdkSample(title: "Raster Overlay", controller:  AnyView(RasterOverlay()))
]
