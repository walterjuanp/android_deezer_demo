package com.example.deezer_service_demo.deezer.data;

public class DeezerArtist {
  private int id;
  private String name;
  private String link;
  private String url;
  private int nb_album;
  private int nb_fan;
  private boolean radio;
  
  public DeezerArtist(){
    id = -1;
    name = null;
    link = null;
    url = null;
    nb_album = 0;
    nb_fan = 0;
    radio = false;
  }

  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getLink() {
    return link;
  }


  public void setLink(String link) {
    this.link = link;
  }


  public String getUrl() {
    return url;
  }


  public void setUrl(String url) {
    this.url = url;
  }


  public int getNb_album() {
    return nb_album;
  }


  public void setNb_album(int nb_album) {
    this.nb_album = nb_album;
  }


  public int getNb_fan() {
    return nb_fan;
  }


  public void setNb_fan(int nb_fan) {
    this.nb_fan = nb_fan;
  }


  public boolean isRadio() {
    return radio;
  }


  public void setRadio(boolean radio) {
    this.radio = radio;
  }


  @Override
  public String toString() {
    return "id: " + id + ", name: " + name;
  }
}
