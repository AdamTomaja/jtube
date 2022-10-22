package ytdlp;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ytdlp.cmd.CommandBuilder;
import ytdlp.parser.CommandLogParser;
import ytdlp.parser.YTVideoInfoParser;

public class JTube {

  private final CommandLogParser logParser = new CommandLogParser();
  private final YTVideoInfoParser videoInfoParser = new YTVideoInfoParser();
  private final JTubeConfig config;

  private final CommandBuilder commandBuilder;

  public JTube(JTubeConfig config) {
    this.config = config;
    commandBuilder = new CommandBuilder(config);
  }

  public DownloadResult downloadVideo(URL url)
      throws IOException, InterruptedException, ExecutionException, JTubeException {
    String command = getCommandBuilder().buildForDownload(url.toExternalForm());
    List<String> commandLog = executeCommand(command);
    return getLogParser().parseResult(commandLog);
  }

  public YTVideoInfo getVideoInfo(URL url)
      throws JTubeException, IOException, ExecutionException, InterruptedException {
    List<String> cmdLog =
        executeCommand(getCommandBuilder().buildForVideoInfo(url.toExternalForm()));
    return getVideoInfoParser().parseInfo(cmdLog);
  }

  private List<String> executeCommand(String command)
      throws IOException, InterruptedException, JTubeException, ExecutionException {
    Process process = getRuntime().exec(command);
    StreamGobbler streamGobbler =
        new StreamGobbler(process.getInputStream(), config.getLogConsumer());
    StreamGobbler errorGobbler =
        new StreamGobbler(process.getErrorStream(), config.getErrorLogConsumer());

    ExecutorService executor = Executors.newFixedThreadPool(2);
    Future<List<String>> future = executor.submit(streamGobbler);
    Future<List<String>> futureError = executor.submit(errorGobbler);
    int exitCode = process.waitFor();

    List<String> log = future.get();
    List<String> errorLog = futureError.get();
    executor.shutdown();

    if (exitCode != 0) {
      throw new JTubeException(
          "Command finished with error", exitCode, logParser.parseError(errorLog), errorLog);
    }

    return log;
  }

  protected Runtime getRuntime() {
    return Runtime.getRuntime();
  }

  protected YTVideoInfoParser getVideoInfoParser() {
    return videoInfoParser;
  }

  protected CommandBuilder getCommandBuilder() {
    return commandBuilder;
  }

  protected CommandLogParser getLogParser() {
    return logParser;
  }
}
