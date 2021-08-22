package events;

import db.DataBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class MessageWhenBotLeaveJoinToGuild extends ListenerAdapter {

    //bot join msg
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        String idGuildJoin = event.getGuild().getId();
        String nameGuild = event.getGuild().getName();
        String regionGuild = event.getGuild().getRegion().getName();
        try {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {

                event.getGuild().getDefaultChannel()
                        .sendMessage(
                                "Thanks for adding " + "**mego**" + " to " + event.getGuild().getName() + "!"
                                        + "\nUse **!help/help/info** for a list of commands."
                                        + "\nCreate text channel with name: `botlog`").queue();

                EmbedBuilder botAddToGuild = new EmbedBuilder();
                botAddToGuild.setColor(0xa224db);
                botAddToGuild.setTitle("Guild added a bot");
                botAddToGuild.setDescription("Guild id: " + idGuildJoin
                        + "\nGuild name: " + nameGuild
                        + "\nGuild region: " + regionGuild);
                BotStart.jda.getGuildById("779317239722672128").getTextChannelById("779321076424376350")
                        .sendMessageEmbeds(botAddToGuild.build()).queue();
                botAddToGuild.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBase.getInstance().createTableGuildWhenBotJoinGuild(idGuildJoin);
        DataBase.getInstance().createTableVoiceWhenBotJoinGuild(idGuildJoin);
        DataBase.getInstance().createDefaultUserInVoice("1", "111111111111111111", idGuildJoin);
    }
}