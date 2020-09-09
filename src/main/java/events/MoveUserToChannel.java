package events;

import java.util.ArrayList;
import java.util.HashMap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MoveUserToChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    try {
      while (true) {
        final HashMap<Integer, Member> users = new HashMap<Integer, Member>();
       // List<Member> memberList = new ArrayList<>();
        users.put(1, event.getMember());
        event.getGuild().moveVoiceMember(users.get(1),
            event.getGuild().getVoiceChannels().get(1))
            .queue();
        event.getGuild().moveVoiceMember(users.get(1),
            event.getGuild().getVoiceChannels().get(0))
            .queue();
        users.remove(1);
        Thread.sleep(1800000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
