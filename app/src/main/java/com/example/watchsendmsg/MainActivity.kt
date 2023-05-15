package com.example.watchsendmsg

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.wearable.*
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMap
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mDataClient: DataClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDataClient = Wearable.getDataClient(this)
    }

    fun sendData(view: View) {
        // Create a data item.
        val dataMap = DataMap()
        dataMap.putString("key", "value")

        // Write the data item to the data layer.
        mDataClient.writeDataItem(dataMap)

        Log.d(TAG, "Data item sent.")
    }

    fun subscribe(view: View) {
        // Create a listener for data events.
        val listener = object : DataClient.OnDataChangedListener {
            override fun onDataChanged(dataEvents: DataEventBuffer) {
                for (event in dataEvents) {
                    // Get the data item from the event.
                    val dataItem = DataMapItem.fromDataMap(event.dataItem)

                    // Get the value of the key.
                    val value = dataItem.getString("key")

                    Log.d(TAG, "Received data: $value")
                }
            }
        }

        // Subscribe to data events.
        mDataClient.registerDataListener(listener)
    }
}
