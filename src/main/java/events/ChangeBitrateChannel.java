package events;

import java.util.Objects;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChangeBitrateChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();
//    User user = event.getChannelJoined().getJDA().getUserById("310364711587676161");
//    boolean memberInVoiceChannel = event.getChannelJoined().getGuild().getMember(user).getVoiceState().inVoiceChannel();

    if (!idUser.equals("310364711587676161")) {
      event.getNewValue().getManager().setBitrate(64000).queue();
    }
    if (idUser.equals("310364711587676161")) {
      event.getNewValue().getManager().setBitrate(45000).queue();
    }
  }
}