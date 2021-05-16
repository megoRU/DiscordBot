package games;

import java.util.HashMap;
import java.util.Map;

public class HangmanRegistry {

  private static final Map<Long, Hangman> activeHangman = new HashMap<>();
  private static volatile HangmanRegistry hangmanRegistry;
  private static final Map<Long, String> guildId = new HashMap<>();
  private static final Map<Long, String> channelId = new HashMap<>();
  private static final Map<Long, String> messageId = new HashMap<>();

  public static HangmanRegistry getInstance() {
    if (hangmanRegistry == null) {
      synchronized (HangmanRegistry.class) {
        if (hangmanRegistry == null) {
          hangmanRegistry = new HangmanRegistry();
        }
      }
    }
    return hangmanRegistry;
  }

  private HangmanRegistry() {}

  public Map<Long, Hangman> getActiveHangman() {
    return activeHangman;
  }

  public Map<Long, String> getGuildId() {
    return guildId;
  }

  public Map<Long, String> getChannelId() {
    return channelId;
  }

  public Map<Long, String> getMessageId() {
    return messageId;
  }

  public void getHangman(long userIdLong) {
    activeHangman.get(userIdLong);
  }

  public void setHangman(long userIdLong, Hangman hangman) {
    activeHangman.put(userIdLong, hangman);
  }

  public boolean hasHangman(long userIdLong) {
    return activeHangman.containsKey(userIdLong);
  }

}
