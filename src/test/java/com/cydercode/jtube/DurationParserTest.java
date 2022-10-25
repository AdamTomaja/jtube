package com.cydercode.jtube;

import java.time.Duration;
import jtube.parser.DurationParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DurationParserTest {

  DurationParser parser = new DurationParser();

  @Test
  void shouldParse() {
    Assertions.assertEquals(Duration.ofSeconds(12 * 60 * 60 + 60 + 5), parser.parse("12:01:05"));
    Assertions.assertEquals(Duration.ofSeconds(12 * 60 * 60 + 60 + 5), parser.parse("12:1:5"));
    Assertions.assertEquals(Duration.ofSeconds(65), parser.parse("01:05"));
    Assertions.assertEquals(Duration.ofSeconds(65), parser.parse("1:05"));
    Assertions.assertEquals(Duration.ofSeconds(5), parser.parse("05"));
  }
}
