//package edu.moravian.csci215.misophoniaapp
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.net.wifi.WifiManager
//
//class WifiScanner() {
//    val wifiManager = MyApplication.applicationContext().getSystemService(MyApplication.applicationContext().WIFI_SERVICE) as WifiManager
//
//    val wifiScanReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action
//            if (action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
//                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
//                if (success) {
//                    val results = wifiManager.scanResults
//                    //todo handle results here
//                }
//            }
//        }
//    }
//
//    val intentFilter = IntentFilter().apply {
//        addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//        addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
//    }
//
//    MyApplication.applicationContext().registerReceiver(wifiScanReceiver, intentFilter)
//
//}