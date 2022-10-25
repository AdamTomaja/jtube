package com.cydercode.jtube.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fragment {
  public String url;
  public double duration;
}
