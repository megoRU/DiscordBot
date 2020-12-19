package events;

import db.DataBase;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.sql.SQLException;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

    private boolean inChannelMeshiva;
    //310364711587676161 - Meshiva //753218484455997491 - megoTEST //250699265389625347 - mego
    private static final String USER_ID_MESHIVA = "310364711587676161";
    //private static final String userIdMego = "250699265389625347";
    private static final String MAIN_GUILD_ID = "250700478520885248";
    //bottestchannel //botchat
    private static final String BOT_CHANNEL_LOGS = "botchat";

    //TODO: Сделать ООП
    private Boolean whoLastEnter(String idUser, String idGuild) throws SQLException {
        DataBase dataBase = new DataBase();
        Map<String, String> whoLast = dataBase.whoLastEnter(idGuild);
        return whoLast.get(idGuild).equals(idUser);
    }

    //TODO: Исправить баг когда бывают случаи, что он не меняет битрейт
    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if (!event.getMember().getUser().isBot()) {
            String idEnterUser = event.getMember().getId();
            String nameEnterUser = event.getMember().getUser().getName();
            String idGuild = event.getGuild().getId();
            try {
                DataBase dataBase = new DataBase();
                String userFromBD = String.valueOf(dataBase.getUserId(idEnterUser, idGuild));
                boolean lastWhoEnter = whoLastEnter(idEnterUser, idGuild);

                if (!userFromBD.equals(idEnterUser)) {
                    dataBase.createDefaultUserInGuild(idEnterUser, nameEnterUser, idGuild);
                    dataBase.setCount(idEnterUser, idGuild);
                }
                if (userFromBD.equals(idEnterUser) && !lastWhoEnter) {
                    dataBase.setWhoLastEnter(1, idGuild, idEnterUser);
                    dataBase.setCount(idEnterUser, idGuild);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            if (idGuild.equals(MAIN_GUILD_ID)) {
                String nameChannelEnterUser = event.getChannelJoined().getName();
                String nameUserWhoEnter = event.getMember().getUser().getName();
                inChannelMeshiva = false;
                event.getGuild().getVoiceChannels()
                        .forEach(e -> e.getMembers()
                                .forEach(f -> {
                                    if (f.getUser().getId().equals(USER_ID_MESHIVA)) {
                                        inChannelMeshiva = true;
                                    }
                                }));
                if (isInChannelMeshiva() && !idEnterUser.equals(USER_ID_MESHIVA)) {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                            .get(0);
                    textChannel.sendMessage(
                            "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                                    + "** зашёл в канал: " + nameChannelEnterUser).queue();
                    return;
                }

                if (isInChannelMeshiva() && idEnterUser.equals(USER_ID_MESHIVA)) {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                            .get(0);
                    textChannel.sendMessage(
                            "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                                    + "** зашёл в канал: " + nameChannelEnterUser).queue();
                    return;
                }

                if (!isInChannelMeshiva() && idEnterUser.equals("250699265389625347")) {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                            .get(0);
                    textChannel.sendMessage(
                            "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                                    + "** зашёл в канал: " + nameChannelEnterUser).queue();
                    return;
                }

                if (!isInChannelMeshiva() && idEnterUser.equals("335466800793911298")) {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                            .get(0);
                    textChannel.sendMessage(
                            "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                                    + "** зашёл в канал: " + nameChannelEnterUser).queue();
                    return;
                }

                if (!isInChannelMeshiva()) {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                            .get(0);
                    textChannel.sendMessage(
                            "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **"
                                    + nameUserWhoEnter
                                    + "** зашёл в канал: " + nameChannelEnterUser).queue();
                }
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        String idGuild = event.getGuild().getId();

        if (!event.getMember().getUser().isBot() && idGuild.equals(MAIN_GUILD_ID)) {

            String idLeaveUser = event.getMember().getId();
            String nameChannelLeaveUser = event.getChannelLeft().getName();
            String nameUserWhoLeave = event.getMember().getUser().getName();
            inChannelMeshiva = false;
            event.getGuild().getVoiceChannels()
                    .forEach(e -> e.getMembers()
                            .forEach(f -> {
                                if (f.getUser().getId().equals(USER_ID_MESHIVA)) {
                                    inChannelMeshiva = true;
                                }
                            }));
            if (idLeaveUser.equals(USER_ID_MESHIVA)) {
                TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                        .get(0);
                textChannel.sendMessage(
                        "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                                + "** вышел из канала: " + nameChannelLeaveUser).queue();
                return;
            }

            if (isInChannelMeshiva()) {
                TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                        .get(0);
                textChannel.sendMessage(
                        "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                                + "** вышел из канала: " + nameChannelLeaveUser).queue();
                return;
            }

            if (!isInChannelMeshiva() && idLeaveUser.equals("250699265389625347")) {
                TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                        .get(0);
                textChannel.sendMessage(
                        "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                                + "** вышел из канала: " + nameChannelLeaveUser).queue();
                return;
            }

            if (!isInChannelMeshiva() && idLeaveUser.equals("335466800793911298")) {
                TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                        .get(0);
                textChannel.sendMessage(
                        "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                                + "** вышел из канала: " + nameChannelLeaveUser).queue();
                return;
            }

            if (!isInChannelMeshiva()) {
                TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
                        .get(0);
                textChannel.sendMessage(
                        "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **"
                                + nameUserWhoLeave
                                + "** вышел в канал: " + nameChannelLeaveUser).queue();
            }
        }
    }

    private boolean isInChannelMeshiva() {
        return inChannelMeshiva;
    }
}