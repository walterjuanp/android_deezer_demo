package com.example.deezer_service_demo.deezer;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

import com.deezer.sdk.DeezerConnect;
import com.deezer.sdk.DeezerError;
import com.deezer.sdk.OAuthException;
import com.deezer.sdk.player.Player;
import com.deezer.sdk.player.TooManyPlayersExceptions;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.impl.DefaultPlayerFactory;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.example.deezer_service_demo.deezer.data.DeezerTrack;

/**
 * Works as Facade for a multiple Tracks player
 */
public class DeezerPlayer {
  private Player            _player;
  private List<DeezerTrack> _tracks;
  private int               _currentPosition;
  
  
  /**
   * Create a new DeezerPlayer
   * @param application
   * @param deezerConnect
   * @throws OAuthException
   * @throws DeezerError
   * @throws TooManyPlayersExceptions
   */
  public DeezerPlayer(Application application, DeezerConnect deezerConnect) throws OAuthException, DeezerError, TooManyPlayersExceptions{
    DefaultPlayerFactory playerFactory = new DefaultPlayerFactory(application, deezerConnect, new WifiAndMobileNetworkStateChecker());
    _player = playerFactory.createPlayer();
    _player.addOnPlayerStateChangeListener(new OnPlayerStateChangeListener() {
      @Override
      public void onPlayerStateChange(PlayerState state, long timePosition) {
        if( state != null ){
          Log.d("DeezerPlayer", "PlayerState => " + state);
        }else{
          Log.d("DeezerPlayer", "PlayerState => NULL");
        }
        
        if( state == PlayerState.PLAYBACK_COMPLETED ){ 
          nextTrack();
        }
      }
    });
    _tracks = new ArrayList<DeezerTrack>();
    _currentPosition = 0;
  }
  
  /**
   * Play the player
   */
  public void play() {
    switch (_player.getPlayerState()) {
    case PAUSED: case READY:
      _player.play();
      break;
    case PLAYBACK_COMPLETED: case STOPPED:
      nextTrack();
      break;
    case STARTED:
      initTrack();
      break;
    default:
      // nothing for the other states
      break;
    }
  }
  
  /**
   * Loads new track and play it
   */
  private void initTrack(){
    if( _tracks.size() == 0 ){ return; }
    
    DeezerTrack track = _tracks.get(_currentPosition);
    stop();
    _player.init(track.getId(), track.getPreview());
    play();
  }
  
  /**
   * Pause the player
   */
  public void pause(){
    PlayerState state = _player.getPlayerState();
    if( state == PlayerState.PLAYING ){
      _player.pause();
    }
  }
  
  /**
   * Stop the player
   */
  public void stop(){
    PlayerState state = _player.getPlayerState();
    if( state != PlayerState.STARTED && state != PlayerState.RELEASED && state != PlayerState.STOPPED ){
      _player.stop();
    }
  }
  
  /**
   * Go to next track or first track. This call goToTrack( current + 1 ).
   */
  public void nextTrack(){
    goToTrack(_currentPosition + 1);
  }
  
  /**
   * Go to previous track or last track. This call goToTrack( current - 1 ).
   */
  public void prevTrack(){
    goToTrack(_currentPosition - 1);
  }
  
  /**
   * Go to specific track by position in list
   * @param position
   */
  public void goToTrack(int position){
    // this is public function, we use Math.abs for check the case that position => -10 and _tracks.size() => 7
    _currentPosition = Math.abs(((position + _tracks.size()) % _tracks.size()));
    initTrack();
  }
  
  /**
   * Go to specific track by trackId if it exists
   * @param trackId
   */
  public void playTrack(int trackId){
    int position = -1;
    for( int i = 0; i < _tracks.size(); i++ ){
      if( trackId == _tracks.get(i).getId() ){
        position = i;
        break;
      }
    }
    
    if( position != -1 ){ goToTrack(position); }
  }
  
  /**
   * Close the player, this stop an release the player.
   */
  public void close(){
    stop();
    if( _player.getPlayerState() == PlayerState.STOPPED ){
      _player.release();
    }
  }

  /**
   * Get tracks
   * @return
   */
  public List<DeezerTrack> getTracks() {
    return _tracks;
  }

  /**
   * Set new tracks, set current to 0 and stop the player.
   * For start the playing you must call play() method.
   * @param tracks
   */
  public void setTracks(List<DeezerTrack> tracks) {
    _currentPosition = 0;
    _tracks = tracks == null ? new ArrayList<DeezerTrack>() : tracks;
    initTrack();
  }

  /**
   * Get the current position of track
   * @return
   */
  public int getCurrentPosition() {
    return _currentPosition;
  }

  
}
