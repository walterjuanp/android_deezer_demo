package com.example.deezer_service_demo.deezer.data;


public class DeezerAlbum {
  private int id;
  private String title;
  private String link;
  private String cover;
  private String label;
  private int nb_tracks;
  private int duration;
  private int rating;
  private String release_date;
  private boolean available;
  private DeezerAlbum alternative;
  private DeezerArtist artist;
  private DeezerData<DeezerTrack> tracks;

  public DeezerAlbum(){
    id = -1;
    title = null;
    link = null;
    cover = null;
    label = null;
    nb_tracks = 0;
    duration = 0;
    rating = 0;
    release_date = null;
    available = false;
    alternative = null;
    artist = null;
    tracks = new DeezerData<DeezerTrack>();
  }
  
  @Override
  public String toString() {
    return "id: " + id + ", name: " + title;
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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getNb_tracks() {
    return nb_tracks;
  }

  public void setNb_tracks(int nb_tracks) {
    this.nb_tracks = nb_tracks;
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

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public DeezerAlbum getAlternative() {
    return alternative;
  }

  public void setAlternative(DeezerAlbum alternative) {
    this.alternative = alternative;
  }

  public DeezerArtist getArtist() {
    return artist;
  }

  public void setArtist(DeezerArtist artist) {
    this.artist = artist;
  }

  public DeezerData<DeezerTrack> getTracks() {
    return tracks;
  }

  public void setTracks(DeezerData<DeezerTrack> tracks) {
    this.tracks = tracks;
  }

}
