package com.cydercode.jtube.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import jtube.JTubeException;
import jtube.YTVideoInfo;
import jtube.parser.YTVideoInfoParser;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class YTVideoInfoParserTest {

  YTVideoInfoParser parser = new YTVideoInfoParser();

  @Test
  void shouldThrowExceptionOnTwoLines() {
    JTubeException ex =
        assertThrows(
            JTubeException.class, () -> parser.parseInfo(Arrays.asList("Test1234", getTestJson())));
    assertEquals("Log bigger than one line", ex.getMessage());
  }

  @Test
  void shouldParseTitleDescriptionAndDuration() throws JTubeException, IOException {
    YTVideoInfo ytVideoInfo = parser.parseInfo(Collections.singletonList(getTestJson()));
    assertEquals("Kiedy nie ma JSONa...", ytVideoInfo.getTitle());
    assertEquals(
        "Kiedy nie ma JSONa... Czyli film o tym dlaczego nie "
            + "można brać nietechnicznego dyrektora na spotkanie programistów.\n"
            + "\n"
            + "#JSONjesttylkojeden #dzwońdoJSONa\n"
            + "\n"
            + "Created by: https://www.codetwo.com",
        ytVideoInfo.getDescription());
    assertEquals(Duration.ofSeconds(35), ytVideoInfo.getDuration());
    assertEquals(LocalDate.of(2017, 11, 16), ytVideoInfo.getUploadDate());
    assertEquals("b4QDxoWlPFw", ytVideoInfo.getVideoId());
  }

  private static String getTestJson() throws IOException {
    try (FileInputStream fs = new FileInputStream("src/test/resources/json-dump.json")) {
      return IOUtils.toString(fs, "UTF-8");
    }
  }
}
