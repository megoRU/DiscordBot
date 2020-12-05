package giftaway;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageGift extends ListenerAdapter {

    private static final String GIFT = "!gift";
    private static final String GIFT_START = "!gift start";
    private static final String GIFT_STOP = "!gift stop";

    //TODO почему то два раза пишет
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim().toLowerCase();
        String prefix = GIFT;
        String prefix2 = GIFT_START;
        String prefix3 = GIFT_STOP;
        if ((message.equals(prefix) || message.equals(prefix2) || message.equals(prefix3))) {

            if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
                prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift";
                prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift start";
                prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift stop";
            }

            if (message.equals(prefix)) {
                long guild = event.getGuild().getIdLong();
                Gift gift;
                gift = new Gift();

                if (!gift.hasGame(guild)) {
                    System.out.println("Игры нет");
                    return;
                }

                //Исключает повторных
                if (gift.hasGame(guild)) {
                    gift = gift.getGame(event.getGuild().getIdLong());
                    if (gift.getListUsersHash(event.getAuthor().getId()) == null) {
                        gift.addUserToPoll(event.getMember().getUser(), event.getGuild(), prefix, event.getChannel());
                        return;
                    }
                }
                return;
            }

            if ((message.equals(prefix2) || message.equals(prefix3)) && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                long guild = event.getGuild().getIdLong();
                Gift gift;
                gift = new Gift();

                if (message.equals(prefix2) && !gift.hasGame(guild)) {
                    gift.setGame(guild, new Gift(event.getGuild()));
                    gift.startGift(event.getGuild(), event.getChannel(), prefix, event);

                }

                if (message.equals(prefix3) && gift.hasGame(guild)) {
                    gift = gift.getGame(event.getGuild().getIdLong());
                    gift.stopGift(event.getGuild(), event.getChannel());
                }

            }

            if (!event.getMember().hasPermission(Permission.ADMINISTRATOR) && (message.equals(prefix2) || message.equals(prefix3))) {
                event.getChannel().sendMessage("You are not Admin").queue();
            }
        }
    }
}