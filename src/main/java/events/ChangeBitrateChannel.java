package events;

import java.util.ArrayList;
import java.util.Objects;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChangeBitrateChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST
  private final String userIdMeshiva = "310364711587676161";
  private final ArrayList<String> listUsersInChannelsForMeshiva = new ArrayList<>();

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String idUser = event.getMember().getUser().getId();
    event.getGuild().getVoiceChannels()
        .forEach(e -> e.getMembers()
        .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

    for (String listlop : listUsersInChannelsForMeshiva) {
      if (listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(true);
        break;
      }
      if (!listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(false);
      }
    }

    if (!idUser.equals(userIdMeshiva) && !isInChannelMeshiva()) {
      event.getNewValue().getManager().setBitrate(96000).queue();
      deleteList();
      return;
    }

    if (!idUser.equals(userIdMeshiva) && isInChannelMeshiva()) {
      event.getNewValue().getManager().setBitrate(45000).queue();
      deleteList();
      return;
    }

    if (idUser.equals(userIdMeshiva)) {
      event.getNewValue().getManager().setBitrate(45000).queue();
      deleteList();
      return;
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();

    if (idUser.matches(userIdMeshiva)) {
      event.getChannelLeft().getManager().setBitrate(96000).queue();
      return;
    }
  }

  public void deleteList() {
    for (int i = 0; i < listUsersInChannelsForMeshiva.size(); i++) {
      listUsersInChannelsForMeshiva.remove(i);
    }
  }

  public boolean isInChannelMeshiva() {
    return inChannelMeshiva;
  }

  public void setInChannelMeshiva(boolean inChannelMeshiva) {
    this.inChannelMeshiva = inChannelMeshiva;
  }
}