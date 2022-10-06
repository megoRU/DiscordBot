package events;

import db.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageWhenBotLeaveJoinToGuild extends ListenerAdapter {

    //bot join msg
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        String idGuildJoin = event.getGuild().getId();
        try {
            if (event.getGuild().getDefaultChannel() != null &&
                            event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL, Permission.MESSAGE_MANAGE) &&
                            event.getGuild().getDefaultChannel() instanceof TextChannel) {

                event.getGuild().getDefaultChannel()
                        .asTextChannel()
                        .sendMessage(
                                "Thanks for adding " + "**mego**" + " to " + event.getGuild().getName() + "!"
                                        + "\nUse **!help/help/info** for a list of commands."
                                        + "\nCreate text channel with name: `botlog`").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBase.getInstance().createTableGuildWhenBotJoinGuild(idGuildJoin);
        DataBase.getInstance().createTableVoiceWhenBotJoinGuild(idGuildJoin);
        DataBase.getInstance().createDefaultUserInVoice("1", "111111111111111111", idGuildJoin);
    }
}