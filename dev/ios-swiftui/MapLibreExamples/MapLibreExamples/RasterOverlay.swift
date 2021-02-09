import SwiftUI
import Mapbox

struct RasterOverlay: View {
    var body: some View {
        MapWithRasterOverlay()
            .navigationTitle("Raster Overlay")
    }
}

struct MapWithRasterOverlay: UIViewRepresentable {
  
    func makeUIView(context: Context) -> MGLMapView {
        // read the key from property list
        let mapTilerKey = Helper.getMapTilerkey()
        Helper.validateKey(mapTilerKey)
        
        // Build the style url
        let styleURL = URL(string: "https://api.maptiler.com/maps/topo/style.json?key=\(mapTilerKey)")
        
        // create the mapview
        let mapView = MGLMapView(frame: .zero, styleURL: styleURL)
        mapView.setCenter(CLLocationCoordinate2D(latitude: 43.457, longitude: -75.789), zoomLevel: 4, animated: false)
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        mapView.tintColor = .darkGray
        
        // use the coordinator only if you need
        // to respond to the map events
        mapView.delegate = context.coordinator
        
        return mapView
    }
    
    func updateUIView(_ uiView: MGLMapView, context: Context) {}
    
    func makeCoordinator() -> MapWithRasterOverlay.Coordinator {
        Coordinator(self)
    }
    
    final class Coordinator: NSObject, MGLMapViewDelegate {
        var control: MapWithRasterOverlay
        
        init(_ control: MapWithRasterOverlay) {
            self.control = control
        }

        func mapViewDidFinishLoadingMap(_ mapView: MGLMapView) {
            // write your custom code which will be executed
            // after map has been loaded
        }
        
        func mapView(_ mapView: MGLMapView, didFinishLoading style: MGLStyle) {
            
            // Set the coordinate bounds for the raster image.
            let coordinates = MGLCoordinateQuad(
                topLeft: CLLocationCoordinate2D(latitude: 46.437, longitude: -80.425),
                bottomLeft: CLLocationCoordinate2D(latitude: 37.936, longitude: -80.425),
                bottomRight: CLLocationCoordinate2D(latitude: 37.936, longitude: -71.516),
                topRight: CLLocationCoordinate2D(latitude: 46.437, longitude: -71.516))
            
            // Create an MGLImageSource, used to add georeferenced raster images to a map.
            if let radarImage = UIImage(named: "radar.gif") {
                let source = MGLImageSource(identifier: "radar", coordinateQuad: coordinates, image: radarImage)
                style.addSource(source)

                // Create a raster layer from the MGLImageSource.
                let radarLayer = MGLRasterStyleLayer(identifier: "radar-layer", source: source)

                // Insert the raster layer below the map's symbol layers.
                for layer in style.layers.reversed() {
                    if !layer.isKind(of: MGLSymbolStyleLayer.self) {
                        style.insertLayer(radarLayer, above: layer)
                        break
                    }
                }
            }
        }
    }
}

struct RasterOverlay_Previews: PreviewProvider {
    static var previews: some View {
        RasterOverlay()
    }
}
