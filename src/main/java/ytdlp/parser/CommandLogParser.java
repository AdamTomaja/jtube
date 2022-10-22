package ytdlp.parser;

import java.util.List;
import java.util.Optional;
import ytdlp.DownloadResult;
import ytdlp.JTubeException;
import ytdlp.error.ErrorResult;

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
    if (line.contains("Video unavailable")) {
      return Optional.of(ErrorResult.VIDEO_UNAVAILABLE);
    } else if (line.contains("Private Video.") || line.contains("Private video.")) {
      return Optional.of(ErrorResult.PRIVATE_VIDEO);
    } else if (line.contains("This video has been removed for violating YouTube")) {
      return Optional.of(ErrorResult.REMOVED_VIOLATION);
    } else if (line.contains("This live stream recording is not available.")) {
      return Optional.of(ErrorResult.LIVESTREAM_UNAVAILABLE);
    } else if (line.contains("HTTP Error 404: Not Found")) {
      return Optional.of(ErrorResult.HTTP_404);
    } else {
      return Optional.empty();
    }
  }
}
