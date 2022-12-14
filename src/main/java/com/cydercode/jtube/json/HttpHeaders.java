package com.cydercode.jtube.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpHeaders {
  @JsonProperty("User-Agent")
  public String userAgent;

  @JsonProperty("Accept")
  public String accept;

  @JsonProperty("Accept-Language")
  public String acceptLanguage;

  @JsonProperty("Sec-Fetch-Mode")
  public String secFetchMode;
}
