package com.cydercode.jtube.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {
  public String url;
  public int preference;
  public String id;
  public int height;
  public int width;
  public String resolution;
}
