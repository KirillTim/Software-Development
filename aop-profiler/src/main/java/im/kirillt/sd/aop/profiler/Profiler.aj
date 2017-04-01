package im.kirillt.sd.aop.profiler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public aspect Profiler {

  private Map<Method, Long> executions = new ConcurrentHashMap<>();
  private Map<Method, Long> totalTime = new ConcurrentHashMap<>();

  public Map<Method, Long> getExecutions() {
    return executions;
  }

  public Map<Method, Long> getTotalTime() {
    return totalTime;
  }

  pointcut packageMethodExecution():
      execution(* im.kirillt.sd.aop.buisness.*.*(..));

  Object around(): packageMethodExecution() {
    long startTime = System.nanoTime();
    try {
      return proceed();
    } finally {
      long executionTime = System.nanoTime() - startTime;
      String signature = thisJoinPoint.getSignature().toShortString();
      Method method = new Method(signature);
      executions.compute(method, (k, v) -> v == null ? 1 : v + 1);
      totalTime.compute(method, (k, v) -> v == null ? executionTime : v + executionTime);
    }
  }

}
