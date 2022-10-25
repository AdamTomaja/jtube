package com.cydercode.jtube;

import java.util.List;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadResult {
  public enum Result {
    NEW,
    EXISTS
  }

  private List<String> log;
  private String resultFile;
  private int logLines;
  private Result result;
}
