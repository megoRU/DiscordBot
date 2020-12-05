package giftaway;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import startbot.BotStart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gift {

    private final ArrayList<String> listUsers = new ArrayList<>();
    private static final HashMap<Long, String> messageId = new HashMap<>();
    private final HashMap<String, String> listUsersHash = new HashMap<>();
    private static final HashMap<Long, Gift> guilds = new HashMap<>();
    private int count = 0;
    private Guild guild;

    public Gift(Guild guild) {
        this.guild = guild;
    }

    public Gift() {
    }

    public void startGift(Guild guild, TextChannel channel, String guildPrefix, GuildMessageReceivedEvent event) {
        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Giftaway starts");
        start.setDescription("Write to participate: " + guildPrefix
                + "\n Users: " + count);

        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();
        List<Message> messages = event.getChannel().getHistory().retrievePast(1).complete();
        messageId.put(guild.getIdLong(), messages.get(0).getId());
    }

    public void addUserToPoll(User user, Guild guild, String guildPrefix, TextChannel channel) {
        count++;
        listUsers.add(user.getId());
        listUsersHash.put(user.getId(), user.getId());
        EmbedBuilder addUser = new EmbedBuilder();
        addUser.setColor(0x00FF00);
        addUser.setTitle(user.getName());
        addUser.setDescription("You are now in the list");
        //Add user to list
        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(addUser.build()).queue();

        EmbedBuilder edit = new EmbedBuilder();
        edit.setColor(0x00FF00);
        edit.setTitle("Giftaway");
        edit.setDescription("Write to participate: " + guildPrefix
                + "\n Users: " + count);

        BotStart.jda.getGuildById(guild.getId()).getTextChannelById(channel.getId())
                .editMessageById(messageId.get(guild.getIdLong()), edit.build()).queue();
        addUser.clear();
        edit.clear();
    }

    public void stopGift(Guild guild, TextChannel channel) {
        int randomWord = (int) Math.floor(Math.random() * listUsers.size());
        String winUser = listUsers.get(randomWord);
        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Giftaway the end");
        start.setDescription("Win: <@" + winUser + ">");
        //Add user to list
        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();
        listUsersHash.clear();
        listUsers.clear();
        messageId.clear();
        removeGame(guild.getIdLong());
    }

    public String getListUsersHash(String id) {
        return listUsersHash.get(id);
    }

    protected HashMap<Long, Gift> getGames() {
        return guilds;
    }

    public void setGame(long userId, Gift game) {
        guilds.put(userId, game);
    }

    public boolean hasGame(long userId) {
        return guilds.containsKey(userId);
    }

    public Gift getGame(long userId) {
        return guilds.get(userId);
    }

    public void removeGame(long userId) {
        guilds.remove(userId);
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

}
