package im.kirillt.sd.aop;

import im.kirillt.sd.aop.profiler.Method;
import im.kirillt.sd.aop.profiler.Profiler;
import im.kirillt.sd.aop.buisness.Logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Profiler profiler = Profiler.aspectOf();

        long totalProgramTime = executeLogic();

        Map<Method, Long> executions = profiler.getExecutions();
        Map<Method, Long> totalTime = profiler.getTotalTime();

        printBenchmarks(totalProgramTime, executions, totalTime);
    }

    private static void printBenchmarks(long totalProgramTime, Map<Method, Long> executions, Map<Method, Long> totalTime) {
        List<Method> ordered = executions.keySet().stream()
                .sorted((o1, o2) -> Long.compare(totalTime.get(o2), totalTime.get(o1)))
                .collect(Collectors.toList());

        System.out.println("Method\t\t\tCount\t\t\tTotal time(ns)\t\t\t%");
        System.out.println("each call, sorted by time:");
        for (Method method : ordered) {
            Long numberOfExecutions = executions.get(method);
            Long totalPointTime = totalTime.get(method);
            double percent = totalPointTime * 1.0 / totalProgramTime;
            System.out.println(String.format("%s\t\t\t%s\t\t\t%s\t\t\t%s", method, numberOfExecutions, totalPointTime, String.format("%.2f", percent)));
        }
        System.out.println("grouped by name:");
        Map<String, Long> nameToTime = new HashMap<>();
        Map<String, Long> nameToExecutions = new HashMap<>();
        for (Method method : ordered) {
            nameToTime.put(method.signature, nameToTime.getOrDefault(method.signature, 0L) + totalTime.get(method));
            nameToExecutions.put(method.signature, nameToExecutions.getOrDefault(method.signature, 0L) + executions.get(method));
        }
        for (String name : nameToTime.keySet()) {
            double percent = nameToTime.get(name) * 1.0 / totalProgramTime;
            System.out.println(String.format("%s\t\t\t%s\t\t\t%s\t\t\t%s", name, nameToExecutions.get(name), nameToTime.get(name), String.format("%.2f", percent)));
        }
        System.out.printf("Total execution time: %d (ns)", totalProgramTime);
    }

    private static long executeLogic() {
        long totalProgramTime = System.nanoTime();
        try {
            Logic logic = new Logic();
            logic.start();
        } catch (InterruptedException ignored) {
        } finally {
            totalProgramTime = System.nanoTime() - totalProgramTime;
        }
        return totalProgramTime;
    }
}
