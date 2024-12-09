package com.christopher.chrismielitz_comp304lab4_ex1.ui.theme

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.christopher.chrismielitz_comp304lab4_ex1.ListOfLocations
import com.christopher.chrismielitz_comp304lab4_ex1.latitude
import com.christopher.chrismielitz_comp304lab4_ex1.longitude
import com.google.android.gms.maps.model.LatLng

abstract class MapsWorker(con: Context, wp: WorkerParameters): CoroutineWorker(con, wp) {
    override suspend fun doWork(): Result {
        return try {
            AddLocation(LatLng(latitude, longitude))
            Result.success()
        }

        catch(exception: Exception)
        {
            Result.failure()
        }
    }
}

public fun AddLocation(latLng: LatLng)
{
    val LongLat = LatLng(latLng.latitude, latLng.longitude)
    ListOfLocations.add(LongLat)
}
