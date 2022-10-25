package com.cydercode.jtube.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import jtube.JTubeException;
import jtube.YTVideoInfo;
import jtube.parser.YTVideoInfoParser;
import org.junit.jupiter.api.Test;

class YTVideoInfoParserTest {

  YTVideoInfoParser parser = new YTVideoInfoParser();

  @Test
  void shouldParseTitleDescriptionAndDuration() throws JTubeException {
    YTVideoInfo info =
        parser.parseInfo("this is the title\n" + "this is the description\n" + "2:34:56");
    assertEquals("this is the title", info.getTitle());
    assertEquals("this is the description", info.getDescription());
    assertEquals(Duration.ofSeconds(2 * 60 * 60 + 34 * 60 + 56), info.getDuration());
  }

  @Test
  void shouldParseOnlyTitleAndDuration() throws JTubeException {
    YTVideoInfo info = parser.parseInfo("video title\n34:12");
    assertEquals("video title", info.getTitle());
    assertEquals(Duration.ofSeconds(34 * 60 + 12), info.getDuration());
    assertEquals("", info.getDescription());
  }

  @Test
  void shouldThrowErrorWhenNull() {
    JTubeException exc =
        assertThrows(
            JTubeException.class, () -> parser.parseInfo((String) null), "Should throw exc");

    assertEquals("Unable to parse video info. Command log empty.", exc.getMessage());
  }

  @Test
  void shouldThrowForOneLine() {
    JTubeException exc =
        assertThrows(
            JTubeException.class,
            () -> parser.parseInfo("one line"),
            "Should throw exception on one line log");

    assertEquals("Unable to parse video info. Less than 2 lines of log", exc.getMessage());
  }

  @Test
  void shouldThrowInvalidDuration() {
    JTubeException exc =
        assertThrows(
            JTubeException.class,
            () -> parser.parseInfo("titlle\ndescription\n12:abc:21"),
            "Should throw when invalid duration string");

    assertEquals("Cannot parse duration of video from last log line", exc.getMessage());
  }
}
