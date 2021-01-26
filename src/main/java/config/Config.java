package config;

public class Config {

  private static final String DEV_BOT_TOKEN = ""; //TOKEN CHANGED
  private static final String PRODUCTION_BOT_TOKEN = ""; //TOKEN CHANGED
  private static final String TOKEN = DEV_BOT_TOKEN; //TOKEN CHANGED
  private static final String CONN = ""; //utf8mb4
  private static final String USER = "";
  private static final String PASS = "";
  private static final String TOP_GG_API_TOKEN = "";
  private static final String BOT_ID = "754093698681274369";
  private static final String STATCRORD = "";

  public static String getTOKEN() {
    return TOKEN;
  }

  public static String getCONN() {
    return CONN;
  }

  public static String getUSER() {
    return USER;
  }

  public static String getPASS() {
    return PASS;
  }

  public static String getTopGgApiToken() {
    return TOP_GG_API_TOKEN;
  }

  public static String getBotId() {
    return BOT_ID;
  }

  public static String getStatcrord() {
    return STATCRORD;
  }
}