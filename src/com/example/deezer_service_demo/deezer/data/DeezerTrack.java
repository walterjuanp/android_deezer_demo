package com.example.deezer_service_demo.deezer.data;


public class DeezerTrack {
  private int id;
  private boolean readable;
  private String title;
  private String isrc;
  private String link;
  private int duration;
  private int rank;
  private String release_date;
  private String preview;
  private DeezerTrack alternative;
  private DeezerAlbum album;
  private DeezerArtist artist;

  public DeezerTrack(){
    id = -1;
    readable = false;
    title = null;
    isrc = null;
    link = null;
    duration = 0;
    rank = 0;
    release_date = null;
    preview = null;
    alternative = null;
    album = null;
    artist = null;
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isReadable() {
    return readable;
  }

  public void setReadable(boolean readable) {
    this.readable = readable;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsrc() {
    return isrc;
  }

  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public String getPreview() {
    return preview;
  }

  public void setPreview(String preview) {
    this.preview = preview;
  }

  public DeezerTrack getAlternative() {
    return alternative;
  }

  public void setAlternative(DeezerTrack alternative) {
    this.alternative = alternative;
  }

  public DeezerAlbum getAlbum() {
    return album;
  }

  public void setAlbum(DeezerAlbum album) {
    this.album = album;
  }

  public DeezerArtist getArtist() {
    return artist;
  }

  public void setArtist(DeezerArtist artist) {
    this.artist = artist;
  }

  @Override
  public String toString() {
    return "id: " + id + ", name: " + title;
  }

}
