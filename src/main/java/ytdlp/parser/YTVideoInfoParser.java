package ytdlp.parser;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import ytdlp.JTubeException;
import ytdlp.YTVideoInfo;

public class YTVideoInfoParser {
  public YTVideoInfo parseInfo(String log) throws JTubeException {
    if (log == null) {
      throw new JTubeException("Unable to parse video info. Command log empty.");
    }

    return parseInfo(Arrays.asList(log.split("\\n")));
  }

  public YTVideoInfo parseInfo(List<String> log) throws JTubeException {
    if (log == null || log.isEmpty()) {
      throw new JTubeException("Unable to parse video info. Command log empty.");
    }

    if (log.size() < 2) {
      throw new JTubeException("Unable to parse video info. Less than 2 lines of log");
    }

    try {
      Duration duration = new DurationParser().parse(log.get(log.size() - 1));
      return YTVideoInfo.builder()
          .title(log.get(0))
          .duration(duration)
          .description(joinDescription(log))
          .build();
    } catch (Exception e) {
      throw new JTubeException("Cannot parse duration of video from last log line", e);
    }
  }

  private String joinDescription(List<String> log) {
    if (log.size() > 2) {
      return String.join("\n", log.subList(1, log.size() - 1));
    }

    return "";
  }
}
