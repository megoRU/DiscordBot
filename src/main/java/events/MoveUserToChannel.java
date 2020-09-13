package events;

import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MoveUserToChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    try {
      String idUser = event.getMember().getUser().getId();
      if (!event.getMember().getUser().isBot() & idUser.equals("310364711587676161")) {
        while (true) {
          event.getChannelJoined().getGuild()
              .moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannels().get(1))
              .queue();
          event.getChannelJoined().getGuild()
              .moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannels().get(0))
              .queue();
          TimeUnit.SECONDS.sleep(1800);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}