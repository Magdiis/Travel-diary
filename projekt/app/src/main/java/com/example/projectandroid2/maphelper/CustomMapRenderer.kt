package com.example.projectandroid2.maphelper

import android.content.Context
import android.graphics.Bitmap

import com.example.projectandroid2.model.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer


class CustomMapRenderer(
    private val context: Context,
    map: GoogleMap,
    manager: ClusterManager<Place>
): DefaultClusterRenderer<Place>(context,map,manager){
    private  val icons: MutableMap<String, Bitmap> = mutableMapOf()

    override fun shouldRenderAsCluster(cluster: Cluster<Place>): Boolean {
        return cluster.size>1
    }

    override fun onBeforeClusterItemRendered(item: Place, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getIcon(item)))
    }

    private fun getIcon(place: Place): Bitmap {
        if (!icons.containsKey(place.type)){
            icons.put(place.type, MarkerUtil.createBitmapMarker(context, place.type))
        }
        return icons[place.type]!!
    }

}
