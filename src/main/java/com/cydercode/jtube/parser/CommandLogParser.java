package com.cydercode.jtube.parser;

import com.cydercode.jtube.DownloadResult;
import com.cydercode.jtube.JTubeException;
import com.cydercode.jtube.error.ErrorResult;
import java.util.List;
import java.util.Optional;

public class CommandLogParser {

  public DownloadResult parseResult(List<String> log) throws JTubeException {
    String outputFile = null;
    DownloadResult.Result result = null;
    int lineCount = 0;

    for (String line : log) {
      if (line.startsWith("[Merger] Merging formats into")) {
        outputFile = line.split(" ")[4].replace("\"", "");
        result = DownloadResult.Result.NEW;
      }

      if (line.endsWith("has already been downloaded")) {
        outputFile = line.split(" ")[1];
        result = DownloadResult.Result.EXISTS;
      }
    }

    if (result == null) {
      throw new JTubeException("Impossible to extract video information");
    }

    return DownloadResult.builder()
        .logLines(lineCount)
        .resultFile(outputFile)
        .result(result)
        .log(log)
        .build();
  }

  public ErrorResult parseError(List<String> log) {
    return log.stream()
        .filter(line -> line.startsWith("ERROR: "))
        .map(this::lineToError)
        .flatMap(Optional::stream)
        .findFirst()
        .orElse(ErrorResult.UNKNOWN);
  }

  private Optional<ErrorResult> lineToError(String line) {
    if(line.contains("This video has been removed by the uploader")) {
      return Optional.of(ErrorResult.REMOVED_BY_UPLOADER);
    } else if (line.contains("Video unavailable")) {
      return Optional.of(ErrorResult.VIDEO_UNAVAILABLE);
    } else if (line.contains("Private Video.") || line.contains("Private video.") || line.contains("This video is private.")) {
      return Optional.of(ErrorResult.PRIVATE_VIDEO);
    } else if (line.contains("This video has been removed for violating YouTube")) {
      return Optional.of(ErrorResult.REMOVED_VIOLATION);
    } else if (line.contains("This live stream recording is not available.")) {
      return Optional.of(ErrorResult.LIVESTREAM_UNAVAILABLE);
    } else if (line.contains("HTTP Error 404: Not Found")) {
      return Optional.of(ErrorResult.HTTP_404);
    } else if (line.contains(
        "Sign in to confirm your age. This video may be inappropriate for some users.")) {
      return Optional.of(ErrorResult.ONLY18PLUS);
    } else if (line.contains("We're processing this video. Check back later.")) {
      return Optional.of(ErrorResult.STILL_PROCESSING);
    } else if (line.contains("Incomplete YouTube ID")) {
      return Optional.of(ErrorResult.INCOMPLETE_ID);
    } else {
      return Optional.empty();
    }
  }
}
