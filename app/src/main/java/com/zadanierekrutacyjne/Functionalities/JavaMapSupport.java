package com.zadanierekrutacyjne.Functionalities;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;

public class JavaMapSupport {
    public static void titleSourceParam(MapView map){
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 512, ".png",
                new String[] { "https://a.tile.openstreetmap.org/" }) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });
    }
}
