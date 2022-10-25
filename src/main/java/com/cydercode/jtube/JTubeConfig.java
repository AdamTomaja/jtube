package com.cydercode.jtube;

import static java.util.function.UnaryOperator.identity;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
public class JTubeConfig {

  private final String ytDlpPath;
  private final String outputDirectory;
  private final String outputFilenameTemplate;
  private final String quality;
  private final Consumer<String> logConsumer;
  private final Consumer<String> errorLogConsumer;

  public static JTubeConfig getDefault() {
    return JTubeConfig.builder()
        .ytDlpPath("yt-dlp")
        .outputDirectory("./")
        .outputFilenameTemplate("%(id)s.%(ext)s")
        .logConsumer(identity()::apply)
        .errorLogConsumer(identity()::apply)
        .build();
  }
}
