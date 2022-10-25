package jtube;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class YTVideoInfo {

  private final String videoId;
  private final Duration duration;
  private final String title;
  private final String description;
  private final boolean isLive;
  private final LocalDate uploadDate;
}
