package com.mypackage.it314_health_center.startups.Testing

import android.os.Looper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.helpers.dbPaths
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList


class LatencyCheck {

    companion object
    {
         fun CalculateDatabaseTime() {
             val start_times = ArrayList<Date>()
             Log.d("123", "Started collecting data ...")
             val end_times = kotlin.collections.ArrayList<Date>()
             for (i in 1..1000){
                 start_times.add(Date())
             end_times.add(Date())
         }
               for(i in 1..1000)
               {
                   start_times[i-1]=Date()
                   FirebaseDatabase.getInstance().reference.child("prescriptions")
                       .child(FirebaseAuth.getInstance().uid!!)
                       .get().addOnSuccessListener {
                           end_times[i-1]= Date()
                       }
               }
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    Log.d("123","Fetched data 1000 times.")
                   var total_time=0L
                   for(i in 1..1000)
                       total_time+=end_times[i-1].time-start_times[i-1].time

                    Log.d("123","Average time = "+total_time/1000+"ms")
                },30000)
         }

        fun checkConcurrentRequests()
        {
            val start_times = ArrayList<Date>()
            Log.d("123", "Started sending request ...")
            val end_times = kotlin.collections.ArrayList<Date>()
            for (i in 1..10){
                start_times.add(Date())
                end_times.add(Date())
            }
            for(i in 1..10)
            {
                start_times[i-1]=Date()
                Log.d("123", "request "+i+" sent")
                FirebaseDatabase.getInstance().reference.child("prescriptions")
                    .child(FirebaseAuth.getInstance().uid!!)
                    .get().addOnSuccessListener {
                        end_times[i-1]= Date()
                        Log.d("123","responsed received for "+i+"th request")
                    }
            }
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                Log.d("123","Received responses for 10 requests")
                var total_time=0L
                var results=""
                for(i in 1..10)
                    results+="\n"+("Request "+i+" : start time : "+start_times[i-1].time +", end time : "+end_times[i-1].time)

                Log.d("123",results)
            },10000)
        }
    }


}