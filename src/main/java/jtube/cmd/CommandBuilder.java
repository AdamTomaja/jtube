package jtube.cmd;

import java.util.Optional;
import jtube.JTubeConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandBuilder {
  private final JTubeConfig config;

  public String buildForDownload(String url) {
    return new StringBuilder()
        .append(config.getYtDlpPath())
        .append(" ")
        .append(buildParam("S", config.getQuality()))
        .append(" ")
        .append(buildParam("P", config.getOutputDirectory()))
        .append(" ")
        .append(buildParam("o", config.getOutputFilenameTemplate()))
        .append(" ")
        .append(url)
        .toString();
  }

  public String buildParam(String option, String value) {
    return Optional.ofNullable(value).map(v -> String.format("-%s %s", option, v)).orElse("");
  }

  public String buildForVideoInfo(String url) {
    return new StringBuilder()
        .append(config.getYtDlpPath())
        .append(" ")
        .append("--dump-single-json ")
        .append(url)
        .toString();
  }
}
