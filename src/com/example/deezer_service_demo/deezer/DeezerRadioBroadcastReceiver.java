package com.example.deezer_service_demo.deezer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeezerRadioBroadcastReceiver extends BroadcastReceiver {
  
  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d("DeezerRadioBroadcastReceiver", "onReceive :: START");
    // TODO Implement it for communication between activity/fragment and service
  };

}
