package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MessagePlayerControl extends ListenerAdapter {

  private static final int DEFAULT_VOLUME = 35; //(0 - 150, where 100 is default max volume)
  private final AudioPlayerManager playerManager;
  private final Map<String, GuildMusicManager> musicManagers;
  private static final String VOLUME = "volume\\s[0-9]{1,3}+";
  private static final String PLAY = "play\\s.+";
  private static final String PPLAY = "pplay\\s.+";
  private static final String LEAVE = "leave";
  private static final String STOP = "stop";
  private static final String SKIP = "skip";
  private static final String PAUSE = "pause";
  private static final String RESTART = "restart";
  private static final String LIST = "list";
  private static final String SHUFFLE = "shuffle";
  private static final String REPEAT = "repeat";
  private static final String RESET = "reset";
  private static final String NP = "np";
  private static final String NOWPLAYING = "nowplaying";

  public MessagePlayerControl() {
    this.playerManager = new DefaultAudioPlayerManager();
    playerManager.registerSourceManager(new YoutubeAudioSourceManager());
    playerManager.registerSourceManager(new BandcampAudioSourceManager());
    playerManager.registerSourceManager(new VimeoAudioSourceManager());
    playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
    playerManager.registerSourceManager(new HttpAudioSourceManager());
    playerManager.registerSourceManager(new LocalAudioSourceManager());
    musicManagers = new HashMap<>();
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (!event.isFromType(ChannelType.TEXT) || event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentRaw().trim();
    if (message.equals("")) {
      return;
    }
    String[] command = message.split(" ", 2);

    String prefixPlay = "!";
    String prefixPPlay = "!";
    String prefixPause = "!";
    String prefixStop = "!";
    String prefixSkip = "!";
    String prefixLeave = "!";
    String prefixRestart = "!";
    String prefixShuffle = "!";
    String prefixList = "!";
    String prefixVolume = "!";
    String prefixRepeat = "!";
    String prefixReset = "!";
    String prefixNowPlaying = "!";
    String prefixNP = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefixPlay = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixPPlay = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixStop = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixPause = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixLeave = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixSkip = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixVolume = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixRestart = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixShuffle = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixList = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixRepeat = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixReset = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixNowPlaying = BotStart.mapPrefix.get(event.getGuild().getId());
      prefixNP = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (prefixCheck.matches("[0-9A-Za-zА-Яа-я]")) {
      prefixCheck = "";
    }

    if (!prefixCheck.equals(prefixPlay) || !prefixCheck.equals(prefixPPlay)
        || !prefixCheck.equals(prefixStop) || !prefixCheck.equals(prefixPause)
        || !prefixCheck.equals(prefixLeave) || !prefixCheck.equals(prefixSkip)
        || !prefixCheck.equals(prefixVolume) || !prefixCheck.equals(prefixRestart)
        || !prefixCheck.equals(prefixShuffle) || !prefixCheck.equals(prefixList)
        || !prefixCheck.equals(prefixRepeat) || !prefixCheck.equals(prefixReset)
        || !prefixCheck.equals(prefixNowPlaying) || !prefixCheck.equals(prefixNP)) {
      return;
    }

    Guild guild = event.getGuild();
    GuildMusicManager mng = getMusicManager(guild);
    AudioPlayer player = mng.player;
    TrackScheduler scheduler = mng.scheduler;

    if (messageWithOutPrefix.equals(LEAVE)) {
      guild.getAudioManager().setSendingHandler(null);
      guild.getAudioManager().closeAudioConnection();
    }

    if (messageWithOutPrefix.matches(PLAY)) {
      if (command.length == 1) {
        if (player.isPaused()) {
          player.setPaused(false);
          event.getChannel().sendMessage("Playback as been resumed.").queue();
        } else if (player.getPlayingTrack() != null) {
          event.getChannel().sendMessage("Player is already playing!").queue();
        } else if (scheduler.queue.isEmpty()) {
          event.getChannel()
              .sendMessage("The current audio queue is empty! Add something to the queue first!")
              .queue();
        }
      } else {   //Commands has 2 parts, .play and url.
        VoiceChannel chan = null;
        try {
          chan = event.getMember().getVoiceState().getChannel();
        } catch (NumberFormatException ignored) {
        }

        if (chan == null) {
          event.getChannel().sendMessage("Could not find VoiceChannel by name: " + command[1])
              .queue();
        } else {
          guild.getAudioManager().setSendingHandler(mng.sendHandler);
          try {
            guild.getAudioManager().openAudioConnection(chan);
          } catch (PermissionException e) {
            if (e.getPermission() == Permission.VOICE_CONNECT) {
              event.getChannel()
                  .sendMessage("mego does not have permission to connect to: " + chan.getName())
                  .queue();
            }
          }
        }

        loadAndPlay(mng, event.getChannel(), command[1], false);
      }
    }

    if (messageWithOutPrefix.matches(PPLAY) && command.length == 2) {
      VoiceChannel chan = null;
      try {
        chan = event.getMember().getVoiceState().getChannel();
      } catch (NumberFormatException ignored) {
      }

      if (chan != null) {
        guild.getAudioManager().setSendingHandler(mng.sendHandler);
        try {
          guild.getAudioManager().openAudioConnection(chan);
        } catch (PermissionException e) {
          if (e.getPermission() == Permission.VOICE_CONNECT) {
            event.getChannel()
                .sendMessage("mego does not have permission to connect to: " + chan.getName())
                .queue();
          }
        }
      }
      loadAndPlay(mng, event.getChannel(), command[1], true);
    }

    if (messageWithOutPrefix.equals(SKIP)) {
      scheduler.nextTrack();
      event.getChannel().sendMessage("The current track was skipped.").queue();
    }

    if (messageWithOutPrefix.equals(PAUSE)) {
      if (player.getPlayingTrack() == null) {
        event.getChannel()
            .sendMessage("Cannot pause or resume player because no track is loaded for playing.")
            .queue();
        return;
      }

      player.setPaused(!player.isPaused());
      if (player.isPaused()) {
        event.getChannel().sendMessage("The player has been paused.").queue();
      } else {
        event.getChannel().sendMessage("The player has resumed playing.").queue();
      }
    }

    if (messageWithOutPrefix.equals(STOP)) {
      scheduler.queue.clear();
      player.stopTrack();
      player.setPaused(false);
      event.getChannel()
          .sendMessage("Playback has been completely stopped and the queue has been cleared.")
          .queue();
    }

    if (messageWithOutPrefix.matches(VOLUME)) {
      if (command.length == 1) {
        event.getChannel().sendMessage("Current player volume: **" + player.getVolume() + "**")
            .queue();
      } else {
        try {
          int newVolume = Math.max(10, Math.min(100, Integer.parseInt(command[1])));
          int oldVolume = player.getVolume();
          player.setVolume(newVolume);
          event.getChannel()
              .sendMessage("Player volume changed from `" + oldVolume + "` to `" + newVolume + "`")
              .queue();
        } catch (NumberFormatException e) {
          event.getChannel().sendMessage("`" + command[1] + "` is not a valid integer. (10 - 100)")
              .queue();
        }
      }
    }

    if (messageWithOutPrefix.equals(RESTART)) {
      AudioTrack track = player.getPlayingTrack();
      if (track == null) {
        track = scheduler.lastTrack;
      }

      if (track != null) {
        event.getChannel().sendMessage("Restarting track: " + track.getInfo().title).queue();
        player.playTrack(track.makeClone());
      } else {
        event.getChannel().sendMessage(
            "No track has been previously started, so the player cannot replay a track!").queue();
      }
    }

    if (messageWithOutPrefix.equals(REPEAT)) {
      scheduler.setRepeating(!scheduler.isRepeating());
      event.getChannel().sendMessage(
          "Player was set to: **" + (scheduler.isRepeating() ? "repeat" : "not repeat") + "**")
          .queue();
    }

    if (messageWithOutPrefix.equals(RESET)) {
      synchronized (musicManagers) {
        scheduler.queue.clear();
        player.destroy();
        guild.getAudioManager().setSendingHandler(null);
        musicManagers.remove(guild.getId());
      }

      mng = getMusicManager(guild);
      guild.getAudioManager().setSendingHandler(mng.sendHandler);
      event.getChannel().sendMessage("The player has been completely reset!").queue();
    }

    if (messageWithOutPrefix.equals(NOWPLAYING) || messageWithOutPrefix.equals(NP)) {
      AudioTrack currentTrack = player.getPlayingTrack();
      if (currentTrack != null) {
        String title = currentTrack.getInfo().title;
        String position = getTimestamp(currentTrack.getPosition());
        String duration = getTimestamp(currentTrack.getDuration());

        String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]",
            title, position, duration);

        event.getChannel().sendMessage(nowplaying).queue();
      } else {
        event.getChannel().sendMessage("The player is not currently playing anything!").queue();
      }
    }

    if (messageWithOutPrefix.equals(LIST)) {
      Queue<AudioTrack> queue = scheduler.queue;
      synchronized (queue) {
        if (queue.isEmpty()) {
          event.getChannel().sendMessage("The queue is currently empty!").queue();
        } else {
          int trackCount = 0;
          long queueLength = 0;
          StringBuilder sb = new StringBuilder();
          sb.append("Current Queue: Entries: ").append(queue.size()).append("\n");
          for (AudioTrack track : queue) {
            queueLength += track.getDuration();
            if (trackCount < 10) {
              sb.append("`[").append(getTimestamp(track.getDuration())).append("]` ");
              sb.append(track.getInfo().title).append("\n");
              trackCount++;
            }
          }
          sb.append("\n").append("Total Queue Time Length: ").append(getTimestamp(queueLength));

          event.getChannel().sendMessage(sb.toString()).queue();
        }
      }
    }

    if (messageWithOutPrefix.equals(SHUFFLE)) {
      if (scheduler.queue.isEmpty()) {
        event.getChannel().sendMessage("The queue is currently empty!").queue();
        return;
      }
      scheduler.shuffle();
      event.getChannel().sendMessage("The queue has been shuffled!").queue();
    }
  }

  private void loadAndPlay(GuildMusicManager mng, final MessageChannel channel, String url,
      final boolean addPlaylist) {
    final String trackUrl;

    //Strip <>'s that prevent discord from embedding link resources
    if (url.startsWith("<") && url.endsWith(">")) {
      trackUrl = url.substring(1, url.length() - 1);
    } else {
      trackUrl = url;
    }

    playerManager.loadItemOrdered(mng, trackUrl, new AudioLoadResultHandler() {
      @Override
      public void trackLoaded(AudioTrack track) {
        String msg = "Adding to queue: " + track.getInfo().title;
        if (mng.player.getPlayingTrack() == null) {
          msg += "\nand the Player has started playing;";
        }

        mng.scheduler.queue(track);
        channel.sendMessage(msg).queue();
      }

      @Override
      public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();
        List<AudioTrack> tracks = playlist.getTracks();

        if (firstTrack == null) {
          firstTrack = playlist.getTracks().get(0);
        }

        if (addPlaylist) {
          channel.sendMessage(
              "Adding **" + playlist.getTracks().size() + "** tracks to queue from playlist: "
                  + playlist.getName()).queue();
          tracks.forEach(mng.scheduler::queue);
        } else {
          channel.sendMessage(
              "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist "
                  + playlist.getName() + ")").queue();
          mng.scheduler.queue(firstTrack);
        }
      }

      @Override
      public void noMatches() {
        channel.sendMessage("Nothing found by " + trackUrl).queue();
      }

      @Override
      public void loadFailed(FriendlyException exception) {
        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
      }
    });
  }

  private GuildMusicManager getMusicManager(Guild guild) {
    String guildId = guild.getId();
    GuildMusicManager mng = musicManagers.get(guildId);
    if (mng == null) {
      synchronized (musicManagers) {
        mng = musicManagers.get(guildId);
        if (mng == null) {
          mng = new GuildMusicManager(playerManager);
          mng.player.setVolume(DEFAULT_VOLUME);
          musicManagers.put(guildId, mng);
        }
      }
    }
    return mng;
  }

  private static String getTimestamp(long milliseconds) {
    int seconds = (int) (milliseconds / 1000) % 60;
    int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
    int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

    if (hours > 0) {
      return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    } else {
      return String.format("%02d:%02d", minutes, seconds);
    }
  }

}