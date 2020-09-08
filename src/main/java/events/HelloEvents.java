package events;

import java.util.Objects;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class HelloEvents extends ListenerAdapter {

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();

    if (nameChannelEnterUser.equals("\uD83D\uDCC0 Илита")) {
      System.out.println(nameUserWhoEnter + " зашел в канал " + nameChannelEnterUser);
    }

    if (nameChannelEnterUser.equals("\uD83C\uDF10 Для всех")) {
      System.out.println(nameUserWhoEnter + " зашел в канал " + nameChannelEnterUser);
    }
    if (nameChannelEnterUser.equals("\uD83D\uDCBF Элита")) {
      System.out.println(nameUserWhoEnter + " зашел в канал " + nameChannelEnterUser);
    }

  }

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();

      // Checks if the bot has permissions.
      //TextChannel channel = event.getChannel();
      TextChannel channel = event.getChannel();

    System.out.println(event.getChannel());

    // Checks if the command is !join.
      if(message.equals("!join")) {

      if (!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
        // The bot does not have permission to join any voice channel. Don't forget the .queue()!
        channel.sendMessage("I do not have permissions to join a voice channel!").queue();
        return;
      }
      // Creates a variable equal to the channel that the user is in.
      VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        assert connectedChannel != null;
        System.out.println(connectedChannel.getMembers());
        // Checks if they are in a channel -- not being in a channel means that the variable = null.
      if (connectedChannel == null) {
        // Don't forget to .queue()!
        channel.sendMessage("You are not connected to a voice channel!").queue();
        return;
      }
      // Gets the audio manager.
      AudioManager audioManager = event.getGuild().getAudioManager();
      // When somebody really needs to chill.
      if (audioManager.isAttemptingToConnect()) {
        channel.sendMessage("The bot is already trying to connect! Enter the chill zone!").queue();
        return;
      }

      // Connects to the channel.
      audioManager.openAudioConnection(connectedChannel);
      // Obviously people do not notice someone/something connecting.
      channel.sendMessage("Connected to the voice channel!").queue();

      if (message.equals("!leave")) { // Checks if the command is !leave.
        // Gets the channel in which the bot is currently connected.
        VoiceChannel connectedChannell = event.getGuild().getSelfMember().getVoiceState()
            .getChannel();
        // Checks if the bot is connected to a voice channel.
        if (connectedChannell == null) {
          // Get slightly fed up at the user.
          channel.sendMessage("I am not connected to a voice channel!").queue();
          return;
        }
        // Disconnect from the channel.
        event.getGuild().getAudioManager().closeAudioConnection();
        // Notify the user.
        channel.sendMessage("Disconnected from the voice channel!").queue();
      }
    }
  }
}