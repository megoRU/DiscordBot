package giftaway;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageGift extends ListenerAdapter {

    private static final String GIFT = "!gift";
    private static final String GIFT_START = "!gift start";
    private static final String GIFT_STOP = "!gift stop";


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim().toLowerCase();

        String prefix = GIFT;
        String prefix2 = GIFT_START;
        String prefix3 = GIFT_STOP;

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift";
            prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift start";
            prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift stop";
        }

        if (message.equals(prefix2) || message.equals(prefix3)) {
            long guild = event.getGuild().getIdLong();
            User user = event.getMember().getUser();
            TextChannel channel = event.getChannel();
            Gift gift;
            gift = new Gift();

            if (message.equals(prefix2) && !gift.hasGame(guild)) {
                //gift.setGame(guild, new Gift(event.getGuild()));

               // gift.setGame(user.getIdLong(), new Gift(event.getGuild()));


                gift.setGame(guild, new Gift(event.getGuild()));


                System.out.println("Пользователей prefix2 " + gift.getListUsers());
                System.out.println("Пользователей prefix2 " + gift);


            }

//            if (gift.hasGame(guild)) {
//                gift = gift.getGame(user.getIdLong());
//
//                gift.startGift(event.getGuild(),
//                        event.getChannel(),
//                        event.getMember().getUser(),
//                        event.getMessage(), prefix);
//                System.out.println("Пользователей prefix2 2 " + gift);
//
//            }


            if (message.equals(prefix3) && gift.hasGame(guild)) {
                gift = gift.getGame(event.getGuild().getIdLong());
                gift.stopGift(event.getGuild(), event.getChannel());
                System.out.println("Пользователей prefix3 " + gift.getListUsers());
                System.out.println("Пользователей prefix3 " + gift);

            }
            return;
        }

        if (message.equals(prefix)) {
            long guild = event.getGuild().getIdLong();
            User user = event.getMember().getUser();
            TextChannel channel = event.getChannel();
            Gift gift;
            gift = new Gift();

            if (!gift.hasGame(guild)) {
                System.out.println("Игры нет");
                return;
            }

            if (gift.hasGame(guild)) {
                gift = gift.getGame(event.getGuild().getIdLong());
                gift.addUserToPoll(event.getMember().getUser(), event.getGuild(), event.getChannel());
                System.out.println("Пользователей prefix " + gift.getListUsers());
                System.out.println("Пользователей prefix " + gift);

            }
        }
    }
}

//            if (message.equals(prefix3) && gift.hasGift(guild)) {
//                gift.setGift(guild, gift.getGuild(event.getGuild().getIdLong()));
//
//                gift.stopGift(event.getGuild(), event.getChannel());
//                return;
//            }
//
//            if (message.equals(prefix2) && !gift.hasGift(guild)) {
//
//                gift.startGift(event.getGuild(),
//                        event.getChannel(),
//                        event.getMember().getUser(),
//                        event.getMessage(), prefix);
//                return;
//            }
//
//
//            ///
//
//            if (!gift.hasGift(guild)) {
//                gift.setGift(guild, new Gift(event.getGuild()));
//            }
//
//
//            if (gift.hasGift(guild)) {
//
//                gift.setGift(guild, new Gift(event.getGuild()));
//
//                if (gift.getListUsersHash(event.getAuthor().getId()) == null) {
//
//                    gift.addUserToPoll(user, event.getGuild(), channel);
//                }
//            }
//
//            ///
////            if (message.equals(prefix) && gift.hasGift(guild)) {
////                gift = gift.getGuild(user.getIdLong());
////
////                gift.addUserToPoll(user);
////            }
////
//
//        }
//
//    }
//}
