package com.cydercode.jtube.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import jtube.error.ErrorResult;
import jtube.parser.CommandLogParser;
import org.junit.jupiter.api.Test;

class CommandLogParserTest {

  CommandLogParser parser = new CommandLogParser();

  @Test
  void shouldParsePrivate() {
    List<String> log =
        log(
            """
            [youtube] _-vB-bie_Oo: Downloading webpage
            [youtube] _-vB-bie_Oo: Downloading android player API JSON
            ERROR: [youtube] _-vB-bie_Oo: Private video. Sign in if you've been granted access to this video
            """);

    assertEquals(ErrorResult.PRIVATE_VIDEO, parser.parseError(log));
  }

  @Test
  void shouldParseViolation() {
    List<String> log =
        log(
            """
            [youtube] ArWkreRiG5A: Downloading webpage
            [youtube] ArWkreRiG5A: Downloading android player API JSON
            ERROR: [youtube] ArWkreRiG5A: This video has been removed for violating YouTube's policy on hate speech. Learn more about combating hate speech in your country.
            """);

    assertEquals(ErrorResult.REMOVED_VIOLATION, parser.parseError(log));
  }

  @Test
  void shouldParseUnavailable() {
    List<String> log =
        log(
            """
            [youtube] yHJwcxpyQOY: Downloading webpage
            [youtube] yHJwcxpyQOY: Downloading android player API JSON
            ERROR: [youtube] yHJwcxpyQOY: Video unavailable
            """);

    assertEquals(ErrorResult.VIDEO_UNAVAILABLE, parser.parseError(log));
  }

  private static List<String> log(String log) {
    return Arrays.asList(log.split("\n"));
  }
}
