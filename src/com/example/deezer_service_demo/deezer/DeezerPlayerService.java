package com.example.deezer_service_demo.deezer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.deezer.sdk.DeezerConnect;
import com.deezer.sdk.DeezerConnectImpl;
import com.deezer.sdk.DeezerError;
import com.deezer.sdk.DeezerRequest;
import com.deezer.sdk.OAuthException;
import com.deezer.sdk.RequestListener;
import com.deezer.sdk.player.TooManyPlayersExceptions;
import com.example.deezer_service_demo.MainActivity;
import com.example.deezer_service_demo.ServiceTestActivity;
import com.example.deezer_service_demo.deezer.data.DeezerPlaylist;
import com.example.deezer_service_demo.deezer.data.DeezerTrack;
import com.google.gson.Gson;

public class DeezerPlayerService extends Service {
  
  public static final String BROADCAST_ACTION    = "com.example.deezer_service_demo.DEEZER_PLAYER_BROADCAST";
  public static final String EXTRA_PARAM__ACTION = "extra-action";
  public static final int    REQUEST_CODE        = 100;
  public static final int    NOTIFICATION_ID     = 200;
  public enum Action{
    PLAY, PAUSE, NEXT, PREVIOUS
  }
  
  
  private static final String DEEZER_APP_ID = "your app id";
  private static final String DEEZER_REQUEST__PLAYLIST = "req-playlist";
  
  private DeezerConnect         _deezerConnect;
  private DeezerPlayer          _dplayer;
  private DeezerRequestHandler  _requesetHandler;
  
  
  // onCreate
  // --------------------------------------------------------------------------------------------------------------------
  @Override
  public void onCreate(){
    Log.d("DeezerPlayerService", "onCreate :: START");
    _deezerConnect = new DeezerConnectImpl( DEEZER_APP_ID );
    _dplayer = null;
    _requesetHandler = new DeezerRequestHandler();
    try {
      _dplayer = new DeezerPlayer(getApplication(), _deezerConnect);
    } catch (OAuthException e) {
      Log.d("DeezerPlayerService", "onCreate :: OAuthException: " + e.getMessage(), e);
    } catch (DeezerError e) {
      Log.d("DeezerPlayerService", "onCreate :: DeezerError: " + e.getMessage(), e);
    } catch (TooManyPlayersExceptions e) {
      Log.d("DeezerPlayerService", "onCreate :: TooManyPlayersExceptions: " + e.getMessage(), e);
    } catch (Exception e){
      Log.d("DeezerPlayerService", "onCreate :: Exception: " + e.getMessage(), e);
    }
    
    if( _dplayer == null ){
      stopSelf();
    }else{
      downloadNewTracks("/playlist/4341978");
    }
    
    openNotification();
    
    super.onCreate();
  }
  
  //onDestroy
  // --------------------------------------------------------------------------------------------------------------------
  @Override
  public void onDestroy(){
    Log.d("DeezerPlayerService", "onDestroy :: START");
    _dplayer.close();
    closeNotification();
    
    super.onDestroy();
  }
  
  //onStartCommand
  // --------------------------------------------------------------------------------------------------------------------
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d("DeezerPlayerService", "onStartCommand :: START");
    
    // check the player
    if( _dplayer == null ){
      stopSelf();
      return Service.START_NOT_STICKY;
    }
    
    // Get the action
    Action action = null;
    String actionStr = intent.getStringExtra(EXTRA_PARAM__ACTION);
    try{
      action = Action.valueOf(actionStr);
    }catch(Exception e){
      Log.d("DeezerPlayerService", "onStartCommand :: Exception on parse action: " + e.getMessage(), e);
    }
    if( action == null ){
      return Service.START_NOT_STICKY;
    }
    
    Log.d("DeezerPlayerService", "onStartCommand :: Run action: " + action);
    switch (action) {
    case PLAY:
      _dplayer.play();
      break;
    case PAUSE:
      _dplayer.pause();
      break;
    case NEXT:
      _dplayer.nextTrack();
      break;
    case PREVIOUS:
      _dplayer.prevTrack();
      break;
    }
    
    return Service.START_NOT_STICKY;
  }
  
  // IBinder
  // --------------------------------------------------------------------------------------------------------------------
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
  
  // Private methods
  // --------------------------------------------------------------------------------------------------------------------
  private void downloadNewTracks(String urlRequest){
    _dplayer.stop();
    DeezerRequest request = new DeezerRequest( urlRequest );
    request.setId(DEEZER_REQUEST__PLAYLIST); // this must be parse for know what is it
    _deezerConnect.requestAsync(request, _requesetHandler);
  }
  
  private void openNotification(){
    Intent notificationIntent = new Intent(getApplicationContext(), ServiceTestActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), REQUEST_CODE,
        notificationIntent, Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
    
    Notification.Builder notiBuild = new Notification.Builder(getApplication());
    notiBuild.setContentTitle("DeezerPlayerService");
    notiBuild.setContentText("The service is running");
    notiBuild.setSmallIcon(com.example.deezer_service_demo.R.drawable.ic_launcher);
    notiBuild.setContentIntent(contentIntent);
    
    Notification noti = notiBuild.getNotification();
    noti.flags |= Notification.FLAG_NO_CLEAR;
    NotificationManager notificationManager = (NotificationManager) getApplicationContext()
        .getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, noti);
  }
  
  private void closeNotification(){
    NotificationManager notiManeger = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    if( notiManeger != null ){
      notiManeger.cancel(NOTIFICATION_ID);
    }
  }
  
  // RequestListener
  // -----------------------------------------------------------------------------------------------------
  class DeezerRequestHandler implements RequestListener {
    
    @Override
    public void onComplete(String response, Object requestId) {
      Log.d("DeezerPlayerService", "DeezerRequestHandler :: onComplete :: START");
      
      List<DeezerTrack> tracks = null;
      if( requestId.equals(DEEZER_REQUEST__PLAYLIST) ) {
        Gson gson = new Gson();
        try{
          DeezerPlaylist playlist = gson.fromJson(response, DeezerPlaylist.class);
          tracks = playlist.getTracks().getData();
        }catch(Exception e){
          Log.d("DeezerPlayerService", "DeezerRequestHandler :: Exception: " + e.getMessage(), e);
        }
      }
      
      if( tracks == null ){
        stopSelf();
      }else{
        _dplayer.setTracks(tracks);
        _dplayer.play();
      }
    }

    @Override
    public void onDeezerError(DeezerError e, Object requestId) {
      Log.d("DeezerPlayerService", "DeezerRequestHandler :: DeezerError: " + e.getErrorCode() + " => " + e.getMessage());
      stopSelf();
    }

    @Override
    public void onIOException(IOException e, Object requestId) {
      Log.d("DeezerPlayerService", "DeezerRequestHandler :: IOException: " + e.getMessage(), e);
      stopSelf();
    }

    @Override
    public void onMalformedURLException(MalformedURLException e, Object requestId) {
      Log.d("DeezerPlayerService", "DeezerRequestHandler :: MalformedURLException: " + e.getMessage(), e);
      stopSelf();
    }

    @Override
    public void onOAuthException(OAuthException e, Object requestId) {
      Log.d("DeezerPlayerService", "DeezerRequestHandler :: onOAuthException: " + e.getMessage(), e);
      stopSelf();
    }
  }

}
