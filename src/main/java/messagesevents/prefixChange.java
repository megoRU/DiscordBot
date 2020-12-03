package messagesevents;

import db.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

import java.sql.SQLException;

public class prefixChange extends ListenerAdapter {

    private static final String PREFIX = "\\*prefix\\s.{1}";
    private static final String PREFIX_RESET = "*prefix reset";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        String[] messages = message.split(" ", 2);

        if ((message.equals(PREFIX_RESET) || message.matches(PREFIX))
                && !event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessage("You cannot change the prefix. You must have permission: `MANAGE_SERVER`").queue();
            return;
        }

        if (message.matches(PREFIX) && event.getMember().hasPermission(Permission.MANAGE_SERVER)
                && BotStart.mapPrefix.get(event.getMessage().getGuild().getId()) != null) {
            BotStart.mapPrefix.put(event.getMessage().getGuild().getId(), messages[1]);
            try {
                DataBase dataBase = new DataBase();
                dataBase.removeDB(event.getMessage().getGuild().getId());
                dataBase.addDB(event.getMessage().getGuild().getId(), messages[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage("The prefix is now: `" + messages[1] + "`").queue();
            return;
        }

        if (message.matches(PREFIX) && event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            BotStart.mapPrefix.put(event.getMessage().getGuild().getId(), messages[1]);
            try {
                DataBase dataBase = new DataBase();
                dataBase.addDB(event.getMessage().getGuild().getId(), messages[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage("The prefix is now: `" + messages[1] + "`").queue();
            return;
        }

        if (message.equals(PREFIX_RESET) && event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            BotStart.mapPrefix.remove(event.getMessage().getGuild().getId());
            try {
                DataBase dataBase = new DataBase();
                dataBase.removeDB(event.getMessage().getGuild().getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage("The prefix is now standard: `!`").queue();
            return;
        }

    }
}
