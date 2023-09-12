package com.cydercode.jtube.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.cydercode.jtube.error.ErrorResult;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CommandLogParserTest {

  CommandLogParser parser = new CommandLogParser();

  @Test
  void shouldParsePrivate() {
    List<String> log =
        log(
            """
            WARNING: [youtube] unable to extract initial player response; please report this issue on  https://github.com/yt-dlp/yt-dlp/issues?q= , filling out the appropriate issue template. Confirm you are on the latest version using  yt-dlp -U
            ERROR: [youtube] _dstwUov5SQ: This video is private. If the owner of this video has granted you access, please sign in.
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

  @Test
  void shouldParseRemovedByUploader() {
    List<String> log =
            log(
                    """
                    ERROR: [youtube] SKiuVEy2G4Y: This video has been removed by the uploader
                    """);

    assertEquals(ErrorResult.REMOVED_BY_UPLOADER, parser.parseError(log));
  }

  @Test
  void shouldParseIncompleteId() {
    List<String> log =
        log(
            """
            ERROR: [youtube:truncated_id] jQY: Incomplete YouTube ID jQY. URL https://youtube.com/watch?v=jQY looks truncated.
            """);

    assertEquals(ErrorResult.INCOMPLETE_ID, parser.parseError(log));
  }

  private static List<String> log(String log) {
    return Arrays.asList(log.split("\n"));
  }
}
