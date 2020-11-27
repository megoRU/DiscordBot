package time;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class UptimeBot {

  private static final long SECONDS_IN_DAY = 86400;
  private static final long SECONDS_IN_HOUR = 3600;
  private static final long SECONDS_IN_MINUTES = 60;
  private static final long SECONDS = 1;

  public static String uptimeBot() {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    long uptime = runtimeMXBean.getUptime();
    long uptimeInSeconds = uptime / 1000;
    long numberOfDays = uptimeInSeconds / SECONDS_IN_DAY;
    long numberOfHours = (uptimeInSeconds % SECONDS_IN_DAY) / SECONDS_IN_HOUR;
    long numberOfMinutes = ((uptimeInSeconds % SECONDS_IN_DAY) % SECONDS_IN_HOUR) / SECONDS_IN_MINUTES;
    long numberOfSeconds = (((uptimeInSeconds % SECONDS_IN_DAY) % SECONDS_IN_HOUR) % SECONDS_IN_MINUTES) / SECONDS;
    return numberOfDays + " " + numberOfHours + " " + numberOfMinutes + " " + numberOfSeconds;
  }
}