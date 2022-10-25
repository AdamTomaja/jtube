package jtube;

import java.util.Collections;
import java.util.List;
import jtube.error.ErrorResult;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JTubeException extends Exception {
  private int exitCode;
  private ErrorResult result = ErrorResult.UNKNOWN;

  private List<String> errorLog = Collections.emptyList();

  public JTubeException(String message) {
    super(message);
  }

  public JTubeException(String message, ErrorResult result, Throwable cause) {
    super(message, cause);
    this.result = result;
  }

  public JTubeException(
      String message, int exitCode, ErrorResult errorResult, List<String> errorLog) {
    super(message);
    this.exitCode = exitCode;
    this.result = errorResult;
    this.errorLog = errorLog;
  }
}
