package events;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChangeBitrateChannel extends ListenerAdapter {

    // Meshiva: 310364711587676161 // megoTEST: 753218484455997491
    private static final String USER_ID_MESHIVA = "310364711587676161";
    private static final String MAIN_GUILD_ID = "250700478520885248";
    private static volatile boolean inChannelMeshiva;

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        String idUser = event.getMember().getUser().getId();
        String idGuild = event.getGuild().getId();

        if (idGuild.equals(MAIN_GUILD_ID)) {
            if (change(idUser, event.getChannelJoined(), event.getNewValue())) return;

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
            if (change(idUser, event.getChannelJoined(), event.getNewValue())) return;

            if (idUser.equals(USER_ID_MESHIVA)) {
                event.getNewValue().getManager().setBitrate(45000).queue();
                event.getChannelLeft().getManager().setBitrate(64000).queue();
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        String idUser = event.getMember().getUser().getId();
        String idGuild = event.getGuild().getId();

        if (idGuild.equals(MAIN_GUILD_ID)) {

            if (idUser.equals(USER_ID_MESHIVA)) {
                event.getChannelLeft().getManager().setBitrate(64000).queue();
            }
        }
    }

    private boolean change(String idUser, AudioChannel channelJoined, AudioChannel newValue) {
        inChannelMeshiva = false;
        channelJoined.getMembers()
                .forEach(member -> {
                    if (member.getUser().getId().equals(USER_ID_MESHIVA)) {
                        inChannelMeshiva = true;
                    }
                });

        if (!idUser.equals(USER_ID_MESHIVA) && !isInChannelMeshiva()) {
            newValue.getManager().setBitrate(64000).queue();
            return true;
        }

        if (!idUser.equals(USER_ID_MESHIVA) && isInChannelMeshiva()) {
            newValue.getManager().setBitrate(45000).queue();
            return true;
        }
        return false;
    }

    public boolean isInChannelMeshiva() {
        return inChannelMeshiva;
    }
}