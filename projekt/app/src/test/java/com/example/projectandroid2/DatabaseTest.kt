package com.example.projectandroid2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.projectandroid2.mock.PlaceMock
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.placedatabase.PlacesDao
import com.example.projectandroid2.placedatabase.PlacesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.util.concurrent.CountDownLatch


//@RunWith(AndroidJUnit4::class)
@RunWith(RobolectricTestRunner::class)
@SmallTest
@Config(sdk = [Config.OLDEST_SDK],manifest = Config.NONE)
class DatabaseTest {
    private lateinit var placesDao: PlacesDao
    private lateinit var db: PlacesDatabase


    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            PlacesDatabase::class.java
        )
           .allowMainThreadQueries()
            .build()
        placesDao = db.placesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun insertPlace() = runTest{
        placesDao.insertPlace(PlaceMock.prague)
        val places = placesDao.getPlacesByCountry(PlaceMock.prague.country).first()
        Assert.assertEquals(places.count(),1)
    }

    @Test
    fun deletePlace() = runBlocking {
        val places = listOf<Place>()
        placesDao.insertPlace(PlaceMock.prague)
        placesDao.delete(PlaceMock.prague)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            placesDao.getPlacesByCountry(PlaceMock.prague.country).collect {
                Assert.assertEquals(places.size,0)
                latch.countDown()
            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }


    @Test
    fun getPlaceByID() = runBlocking {
        placesDao.insertPlace(PlaceMock.prague)
        val place = placesDao.getPlaceByID(1)
        Assert.assertEquals(place.name,PlaceMock.prague.name)
    }


}
