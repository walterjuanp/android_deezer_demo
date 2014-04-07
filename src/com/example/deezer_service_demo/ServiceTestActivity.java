package com.example.deezer_service_demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.deezer_service_demo.deezer.DeezerPlayerService;

public class ServiceTestActivity extends Activity implements View.OnClickListener {

  private Button _playBtn;
  private Button _stopBtn;
  private Button _pauseBtn;
  private Button _nextBtn;
  private Button _prevBtn;

  // handler for received Intents for the "my-event" event
  private BroadcastReceiver _broadcast = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver", "onReceive :: START ");
    }
  };
  
  @Override
  public void onResume() {
    super.onResume();
    IntentFilter mStatusIntentFilter = new IntentFilter(DeezerPlayerService.BROADCAST_ACTION);
    LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(_broadcast, mStatusIntentFilter);
  }
  
  @Override
  public void onPause() {
    LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(_broadcast);
    super.onPause();
  } 
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_test);
    _playBtn = (Button) findViewById(R.id.play);
    _pauseBtn = (Button) findViewById(R.id.pause);
    _stopBtn = (Button) findViewById(R.id.stop);
    _prevBtn = (Button) findViewById(R.id.prev);
    _nextBtn = (Button) findViewById(R.id.next);

    _playBtn.setOnClickListener(this);
    _pauseBtn.setOnClickListener(this);
    _stopBtn.setOnClickListener(this);
    _prevBtn.setOnClickListener(this);
    _nextBtn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent mServiceIntent;
    switch (v.getId()) {
    case R.id.play:
      mServiceIntent = new Intent(getApplicationContext(), DeezerPlayerService.class);
      mServiceIntent.putExtra(DeezerPlayerService.EXTRA_PARAM__ACTION, DeezerPlayerService.Action.PLAY.toString());
      getApplicationContext().startService(mServiceIntent);
      break;
    case R.id.stop:
      mServiceIntent = new Intent(getApplicationContext(), DeezerPlayerService.class);
      getApplicationContext().stopService(mServiceIntent);
      break;
    case R.id.pause:
      mServiceIntent = new Intent(getApplicationContext(), DeezerPlayerService.class);
      mServiceIntent.putExtra(DeezerPlayerService.EXTRA_PARAM__ACTION, DeezerPlayerService.Action.PAUSE.toString());
      getApplicationContext().startService(mServiceIntent);
      break;
    case R.id.prev:
      mServiceIntent = new Intent(getApplicationContext(), DeezerPlayerService.class);
      mServiceIntent.putExtra(DeezerPlayerService.EXTRA_PARAM__ACTION, DeezerPlayerService.Action.PREVIOUS.toString());
      getApplicationContext().startService(mServiceIntent);
      break;
    case R.id.next:
      mServiceIntent = new Intent(getApplicationContext(), DeezerPlayerService.class);
      mServiceIntent.putExtra(DeezerPlayerService.EXTRA_PARAM__ACTION, DeezerPlayerService.Action.NEXT.toString());
      getApplicationContext().startService(mServiceIntent);
      break;
    }
  }
}