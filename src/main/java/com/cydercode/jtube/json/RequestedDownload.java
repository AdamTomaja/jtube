package com.cydercode.jtube.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestedDownload {
  public ArrayList<RequestedFormat> requested_formats;
  public String format;
  public String format_id;
  public String ext;
  public String protocol;
  public String format_note;
  public int filesize_approx;
  public double tbr;
  public int width;
  public int height;
  public String resolution;
  public int fps;
  public String dynamic_range;
  public String vcodec;
  public double vbr;
  public String acodec;
  public double abr;
  public int asr;
  public int epoch;
  public String _filename;
  public boolean __write_download_archive;
}
