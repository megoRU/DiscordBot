package time;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class UptimeBot {

  public UptimeBot() {
  }

  public String uptimeBot() {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    long uptime = runtimeMXBean.getUptime();
    long uptimeInSeconds = uptime / 1000;
    long numberOfHours = uptimeInSeconds / (60 * 60);
    long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
    long numberOfSeconds = uptimeInSeconds % 60;
    return String.valueOf(numberOfHours) + " " + String.valueOf(numberOfMinutes) + " " + String.valueOf(numberOfSeconds);
  }
}
