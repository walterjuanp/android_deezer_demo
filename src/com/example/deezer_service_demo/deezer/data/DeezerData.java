package com.example.deezer_service_demo.deezer.data;

import java.util.ArrayList;
import java.util.List;

public class DeezerData<T> {
  private List<T> data;
  
  public DeezerData(){
    data = new ArrayList<T>();
  }

  public T get(int location){
    return data.get(location);
  }
  
  public boolean empty(){
    return data == null || data.size() == 0;
  }
  
  public int size(){
    return data == null ? 0 : data.size();
  }
  
  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
  
}
