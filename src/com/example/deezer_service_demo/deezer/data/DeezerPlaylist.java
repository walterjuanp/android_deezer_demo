package com.example.deezer_service_demo.deezer.data;


public class DeezerPlaylist {
  private int id;
  private String title;
  private String description;
  private int duration;
  private int rating;
  private String link;
  private String picture;
  private DeezerData<DeezerTrack> tracks;
  
  public DeezerPlaylist(){
    id = -1;
    title = null;
    description = null;
    duration = 0;
    rating = 0;
    link = null;
    picture = null;
    tracks = new DeezerData<DeezerTrack>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public DeezerData<DeezerTrack> getTracks() {
    return tracks;
  }

  public void setTracks(DeezerData<DeezerTrack> tracks) {
    this.tracks = tracks;
  }
  
}
 