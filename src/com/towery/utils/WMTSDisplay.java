package com.towery.utils;

import android.os.Environment;
import com.easymap.android.maps.v3.geometry.Envelope;
import com.easymap.android.maps.v3.geometry.SpatialReference;
import com.easymap.android.maps.v3.layers.TiledLayer.TileInfo;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayer;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayerInfo;

public class WMTSDisplay
{
  public static final String LAYER_SHILIANG = "Shiliang_2012";
  public static final String LAYER_SATE_RV = "Sate_Rv_2012";
  public static final String LAYER_25D = "25D_2012";
  public static final String LAYER_DEM = "DEM_2012";
  public static final String LAYER_SATE = "Sate_2012";

  public static WMTSLayer getLayer(String lyrName)
  {
    WMTSLayerInfo layerInfo = new WMTSLayerInfo();
    layerInfo.setVersion("1.0.0");
    layerInfo.setFormat("image/png");
    layerInfo.setLayerName(lyrName);
    layerInfo.setStyle("default");
    layerInfo.setTileMatrixSet(lyrName);
    SpatialReference sr = SpatialReference.create(2436);

    WMTSLayer xxzyzx = new WMTSLayer("http://172.28.255.23:10000/service/ImageEngine/picdis/abc?", layerInfo, 
      sr, 
      new Envelope(371987.18333999999D, 252920.58593D, 
      624459.12035999994D, 423400.07714000001D), 
      "wmts_proj.cache", Environment.getExternalStorageDirectory() + "/" + "FounderMap/wmts/yxzyzx/" + lyrName);
    xxzyzx.setMinLevel(0);
    xxzyzx.setMaxLevel(12);
    int levels = 13;
    double baseScale = 3386781.4960629921D;
    double baseResolution = 896.0859375D;
    double[] scale = new double[levels];
    double[] res = new double[levels];
    for (int i = 0; i < levels; ++i) {
      scale[i] = (baseScale / Math.pow(2.0D, i));
      res[i] = (baseResolution / Math.pow(2.0D, i));
    }
    TileInfo tileInfo = new TileInfo(0D, 688194.0D, scale, res, levels, 90, 256, 256);
    xxzyzx.setTileInfo(tileInfo);
    xxzyzx.setVisible(true);

    return xxzyzx;
  }
}