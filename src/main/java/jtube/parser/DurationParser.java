package jtube.parser;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DurationParser {
  private final int[] multipliers = new int[] {1, 60, 60 * 60};

  public Duration parse(String durationString) {
    List<String> splitList = Arrays.asList(durationString.split(":"));
    Collections.reverse(splitList);
    return Duration.ofSeconds(
        IntStream.range(0, splitList.size())
            .map(i -> Integer.parseInt(splitList.get(i)) * multipliers[i])
            .sum());
  }
}
