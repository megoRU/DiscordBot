package events;

import java.util.ArrayList;
import java.util.Objects;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChangeBitrateChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST
  private final String userIdMeshiva = "310364711587676161";
  private final ArrayList<String> listUsersInChannelsForMeshiva = new ArrayList<>();
  public final String MAIN_GUILD_ID = "250700478520885248";


  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String idUser = event.getMember().getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {
      event.getGuild().getVoiceChannels()
              .forEach(e -> e.getMembers()
                      .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));
      for (String listLoop : listUsersInChannelsForMeshiva) {
        if (listLoop.equals(userIdMeshiva)) {
          inChannelMeshiva = true;
          break;
        }
        inChannelMeshiva = false;
      }

      if (!idUser.equals(userIdMeshiva) && !isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(64000).queue();
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
      }
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    String idUser = event.getMember().getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {
      event.getGuild().getVoiceChannels()
              .forEach(e -> e.getMembers()
                      .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

      for (String listLoop : listUsersInChannelsForMeshiva) {
        if (listLoop.equals(userIdMeshiva)) {
          inChannelMeshiva = true;
          break;
        }
        inChannelMeshiva = false;
      }

      if (!idUser.equals(userIdMeshiva) && !isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(64000).queue();
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
      }
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {

      if (idUser.equals(userIdMeshiva)) {
        event.getChannelLeft().getManager().setBitrate(64000).queue();
      }
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
}