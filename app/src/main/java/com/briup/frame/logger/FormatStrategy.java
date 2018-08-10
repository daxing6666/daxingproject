package com.briup.frame.logger;

public interface FormatStrategy {

  void log(int priority, String tag, String message);
}
