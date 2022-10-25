package com.cydercode.jtube.parser;

import com.cydercode.jtube.JTubeException;
import com.cydercode.jtube.YTVideoInfo;
import com.cydercode.jtube.error.ErrorResult;
import com.cydercode.jtube.json.JsonDump;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class YTVideoInfoParser {

  private final DateTimeFormatter UPLOAD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

  public YTVideoInfo parseInfo(String log) throws JTubeException {
    if (log == null) {
      throw new JTubeException("Unable to parse video info. Command log empty.");
    }

    return parseInfo(Arrays.asList(log.split("\\n")));
  }

  public YTVideoInfo parseInfo(List<String> log) throws JTubeException {
    if (log.size() > 1) {
      throw new JTubeException("Log bigger than one line");
    }

    try {
      JsonDump jsonDump = new ObjectMapper().readValue(log.get(0), JsonDump.class);
      return YTVideoInfo.builder()
          .videoId(jsonDump.id)
          .title(jsonDump.fulltitle)
          .description(jsonDump.description)
          .duration(Duration.ofSeconds(jsonDump.duration))
          .isLive(jsonDump.is_live)
          .uploadDate(LocalDate.parse(jsonDump.upload_date, UPLOAD_DATE_FORMATTER))
          .build();
    } catch (JsonProcessingException e) {
      throw new JTubeException("Cannot parse json-dump", ErrorResult.UNKNOWN, e);
    }
  }
}
