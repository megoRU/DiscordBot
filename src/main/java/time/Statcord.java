package time;

import com.sun.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import net.dv8tion.jda.api.JDA;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.TimerTask;
import oshi.SystemInfo;

public class Statcord {

  private static boolean statcordActive = false;
  private static int servers;
  private static int users;
  private static int commandsRun;
  private static String key;
  private static String id;
  private static int memactive;
  private static int memload;
  private static int cpuload;
  private static long bandwidth; // need help pls

  private static String custom1 = "empty";
  private static String custom2 = "empty";

  private static JDA jda = null;
  private static JSONArray popcmd = new JSONArray();
  private static JSONArray activeuser = new JSONArray();
  private static boolean autopost = false;

  private static int time = 60; // autopost timer in min
  private static final SystemInfo si = new SystemInfo();
  private static String NetworkName = "";
  private static int count;

  public static void start(String id, String key, JDA jda, boolean autopost, int timerInMin)
      throws Exception {
    System.out.println(
        "\u001B[33mStatcord started with this: " + id + " " + key + " " + jda.toString()
            + "\u001B[0m");

    //save important stuff
    Statcord.jda = jda;
    Statcord.key = key;
    Statcord.id = id;

    //make it active
    statcordActive = true;

    time = timerInMin;
    getNetworkName();

    if (autopost) {
      autorun();
      System.out.println("\u001B[33m!!! [Statcord] autorun activated!\u001B[0m");
      Statcord.autopost = true;
    }
  }

  //manually updating Stats
  public static void updateStats() throws IOException, InterruptedException {
    if (!statcordActive) {
      System.out.println("\u001B[33m[Statcord]You can not use 'updateStats' because Statcord is not active!\u001B[0m");
      return;
    }
    System.out.println("\u001B[33m[Statcord] Updating Statcord!\u001B[0m");

    servers = jda.getGuilds().size();
    users = jda.getUsers().size();
    memactive = (int) Runtime.getRuntime().totalMemory();
    memload = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    double mem = ((double) memload / (double) memactive) * (double) 100;
    int memperc = (int) Math.round(mem);

    for (int i = 0; i < si.getHardware().getNetworkIFs().size(); i++) {
      count++;
      if (si.getHardware().getNetworkIFs().get(i).getName().equals(NetworkName)) {
        System.out.println(si.getHardware().getNetworkIFs().get(i).getName());
        break;
      }
    }

    long down = si.getHardware().getNetworkIFs().get(count).getBytesRecv();
    long up = si.getHardware().getNetworkIFs().get(count).getBytesSent();
    bandwidth = down + up;

    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
        OperatingSystemMXBean.class);

    double processload = osBean.getSystemCpuLoad();
    cpuload = (int) (processload * 100);

    JSONObject post = new JSONObject();
    post.put("id", id);
    post.put("key", key);
    post.put("servers", String.valueOf(servers));
    post.put("users", String.valueOf(users));
    post.put("active", activeuser);
    post.put("commands", String.valueOf(commandsRun));
    post.put("popular", popcmd);
    post.put("memactive", String.valueOf(memload));
    post.put("memload", String.valueOf(memperc));
    post.put("cpuload", String.valueOf(cpuload));
    post.put("bandwidth", String.valueOf(bandwidth));
    if (!custom1.equalsIgnoreCase("empty")) {
      post.put("custom1", custom1);
    }
    if (!custom2.equalsIgnoreCase("empty")) {
      post.put("custom2", custom2);
    }

    String body = post.toString();

    post(body);

    commandsRun = 0;
    popcmd = new JSONArray();
    activeuser = new JSONArray();
    custom2 = "empty";
    custom1 = "empty";
  }

  //http post to statcord
  private static void post(String body) throws IOException, InterruptedException {
    String url = "https://statcord.com/logan/stats";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .header("Content-Type", "application/json")
        .build();

    HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

    if (response.body().contains("Success")) {
      System.out.println("\u001B[33m[Statcord] Updated Stats on Statcord!\u001B[0m");
    } else {
      System.out.println("[Statcord] An error happened");
      System.out.println(response.body());
    }
  }

  //autorun set to 1h
  public static void autorun() {
    Timer timer = new Timer();

    timer.schedule(new TimerTask() {
      public void run() throws NullPointerException {
        System.out.println("\u001B[33m[Statcord] Automatic update!\u001B[0m");
        try {
          updateStats();
        } catch (IOException | InterruptedException e) {
          Thread.currentThread().interrupt();
          e.printStackTrace();
        }
      }
    }, 5000, time * 60000L);
  }

  public static void getNetworkName() throws Exception {

    final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

    // get hostname
    InetAddress myAddr = InetAddress.getByName(si.getOperatingSystem().getNetworkParams().getHostName());

    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface networkInterface = networkInterfaces.nextElement();
      Enumeration<InetAddress> inAddrs = networkInterface.getInetAddresses();
      while (inAddrs.hasMoreElements()) {
        InetAddress inAddr = inAddrs.nextElement();
        if (inAddr.equals(myAddr)) {
          NetworkName = networkInterface.getName();
          return;
        }
      }
    }
    throw new Exception("Not found network hostname");
  }
}
