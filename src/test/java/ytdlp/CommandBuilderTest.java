package ytdlp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ytdlp.cmd.CommandBuilder;

class CommandBuilderTest {

  @Test
  void shouldBuildForDownload() {
    // given
    String expected =
        "/usr/bin/yt-dlp -S res:720 -P /media/yt -o movie.%(ext)s http://youtube.com/movie";
    CommandBuilder cb = createBuilder();

    // when
    String result = cb.buildForDownload("http://youtube.com/movie");

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldBuildSingleParam() {
    assertEquals("-O val", new CommandBuilder(JTubeConfig.getDefault()).buildParam("O", "val"));
  }

  @Test
  void shouldReturnEmptyString() {
    Assertions.assertEquals("", new CommandBuilder(JTubeConfig.getDefault()).buildParam("O", null));
  }

  @Test
  void shouldBuildForVideoInfo() {
    Assertions.assertEquals(
        "/usr/bin/yt-dlp --get-title --get-description --get-duration http://youtube.com/video",
        createBuilder().buildForVideoInfo("http://youtube.com/video"));
  }

  private static CommandBuilder createBuilder() {
    return new CommandBuilder(
        JTubeConfig.builder()
            .outputDirectory("/media/yt")
            .quality("res:720")
            .ytDlpPath("/usr/bin/yt-dlp")
            .outputFilenameTemplate("movie.%(ext)s")
            .build());
  }
}
