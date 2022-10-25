package com.cydercode.jtube.json;

import java.util.ArrayList;

public class Format {
  public String format_id;
  public String format_note;
  public String ext;
  public String protocol;
  public String acodec;
  public String vcodec;
  public String url;
  public int width;
  public int height;
  public ArrayList<Fragment> fragments;
  public String audio_ext;
  public String video_ext;
  public String format;
  public String resolution;
  public HttpHeaders http_headers;
  public int asr;
  public int filesize;
  public int source_preference;
  public int fps;
  public int quality;
  public boolean has_drm;
  public double tbr;
  public String language;
  public int language_preference;
  public int preference;
  public String dynamic_range;
  public double abr;
  public DownloaderOptions downloader_options;
  public String container;
  public double vbr;
  public double filesize_approx;
}
