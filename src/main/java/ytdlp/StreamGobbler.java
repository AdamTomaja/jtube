package ytdlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import ytdlp.error.ErrorResult;

class StreamGobbler implements Callable<List<String>> {
  private final InputStream inputStream;
  private final Consumer<String> consumer;

  public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
    this.inputStream = inputStream;
    this.consumer = consumer;
  }

  @Override
  public List<String> call() throws JTubeException {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      List<String> lines = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        lines.add(line);
        consumer.accept(line);
      }
      return lines;
    } catch (IOException e) {
      throw new JTubeException(
          "Error when processing log stream input", ErrorResult.JTUBE_ERROR, e);
    }
  }
}
