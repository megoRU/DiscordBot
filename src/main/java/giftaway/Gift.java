package giftaway;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import startbot.BotStart;

import java.util.ArrayList;
import java.util.HashMap;

public class Gift {

    private final ArrayList<String> listUsers = new ArrayList<>();
    private final HashMap<String, String> listUsersHash = new HashMap<>();
    private static final HashMap<Long, Gift> guilds = new HashMap<>();
    private boolean isLetterPresent;
    private User user;
    private Guild guild;
    private TextChannel channel;
    private Message message;
    private String guildPrefix;

    public Gift(Guild guild) {
        this.guild = guild;
    }

    public Gift() {
    }

    public void startGift(Guild guild, TextChannel channel, User user, Message message, String guildPrefix) {
        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Giftaway starts");
        start.setDescription("Write to participate: "+ guildPrefix);

        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();
    }

    public void addUserToPoll(User user, Guild guild, TextChannel channel) {
        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Giftaway starts");
        start.setDescription("You are now in the list");
        //Add user to list
        listUsers.add(user.getId());
        listUsersHash.put(user.getId(), user.getId());
        System.out.println(getListUsers());
        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();

    }

    public void stopGift(Guild guild, TextChannel channel) {
        System.out.println(listUsers);
        int randomWord = (int) Math.floor(Math.random() * listUsers.size());

        System.out.println(listUsers);
        String winUser = listUsers.get(randomWord);

        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Giftaway stop");
        start.setDescription("Win: <@" + winUser + ">");
        //Add user to list
        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();
        listUsersHash.clear();
        listUsers.clear();
        removeGame(guild.getIdLong());
    }

    public ArrayList<String> getListUsers() {
        return listUsers;
    }

    public HashMap<String, String> getListUsersHash() {
        return listUsersHash;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public TextChannel getChannel() {
        return channel;
    }

    private boolean isIsLetterPresent() {
        return isLetterPresent;
    }

}
