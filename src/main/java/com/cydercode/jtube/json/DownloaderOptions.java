package com.cydercode.jtube.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloaderOptions {
  public int http_chunk_size;
}
