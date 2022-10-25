package jtube;

import java.time.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class YTVideoInfo {

  private final Duration duration;
  private final String title;
  private final String description;
}
