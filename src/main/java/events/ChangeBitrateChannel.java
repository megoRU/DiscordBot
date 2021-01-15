package events;

import java.util.Objects;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChangeBitrateChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST
  private static final String USER_ID_MESHIVA = "310364711587676161";
  private static final String MAIN_GUILD_ID = "250700478520885248";

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String idUser = event.getMember().getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {
      inChannelMeshiva = false;
      event.getGuild().getVoiceChannels()
          .forEach(e -> e.getMembers()
              .forEach(f -> {
                if (f.getUser().getId().equals(USER_ID_MESHIVA)) {
                  inChannelMeshiva = true;
                }
              }));

      if (!idUser.equals(USER_ID_MESHIVA) && !isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(64000).queue();
        return;
      }

      if (!idUser.equals(USER_ID_MESHIVA) && isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(45000).queue();
        return;
      }

      if (idUser.equals(USER_ID_MESHIVA)) {
        event.getNewValue().getManager().setBitrate(45000).queue();
      }
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    String idUser = event.getMember().getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {
      inChannelMeshiva = false;
      event.getGuild().getVoiceChannels()
          .forEach(e -> e.getMembers()
              .forEach(f -> {
                if (f.getUser().getId().equals(USER_ID_MESHIVA)) {
                  inChannelMeshiva = true;
                }
              }));

      if (!idUser.equals(USER_ID_MESHIVA) && !isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(64000).queue();
        return;
      }

      if (!idUser.equals(USER_ID_MESHIVA) && isInChannelMeshiva()) {
        event.getNewValue().getManager().setBitrate(45000).queue();
        return;
      }

      if (idUser.equals(USER_ID_MESHIVA)) {
        event.getNewValue().getManager().setBitrate(45000).queue();
      }
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();
    String idGuild = event.getGuild().getId();

    if (idGuild.equals(MAIN_GUILD_ID)) {

      if (idUser.equals(USER_ID_MESHIVA)) {
        event.getChannelLeft().getManager().setBitrate(64000).queue();
      }
    }
  }

  public boolean isInChannelMeshiva() {
    return inChannelMeshiva;
  }
}