package ytdlp;

import lombok.Getter;
import lombok.ToString;
import ytdlp.error.ErrorResult;

@Getter
@ToString
public class JTubeException extends Exception {
  private int exitCode;
  private ErrorResult result;

  public JTubeException(String message) {
    super(message);
  }

  public JTubeException(String message, Throwable cause) {
    super(message, cause);
  }

  public JTubeException(String message, int exitCode, ErrorResult errorResult) {
    super(message);
    this.exitCode = exitCode;
    this.result = errorResult;
  }
}
