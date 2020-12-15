package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessagePlayMusic extends ListenerAdapter {

    private static final String PLAY = "!play\\s.+";
    private static final String STOP = "!stop";
    private static final String SKIP = "!skip";
    private static final String VOLUME = "!volume\\s[0-9]{1,3}+";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String prefixPlay = PLAY;
        String prefixStop = STOP;
        String prefixSkip = SKIP;
        String prefixVolume = VOLUME;

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefixPlay = BotStart.mapPrefix.get(event.getGuild().getId()) + "play\\s.+";
            prefixStop = BotStart.mapPrefix.get(event.getGuild().getId()) + "stop";
            prefixSkip = BotStart.mapPrefix.get(event.getGuild().getId()) + "skip";
            prefixVolume = BotStart.mapPrefix.get(event.getGuild().getId()) + "volume\\s[0-9]{1,3}+";
        }

        String message = event.getMessage().getContentDisplay().trim();
        GuildVoiceState guildVoiceState = event.getMember().getVoiceState();
        Guild guild = event.getGuild();

        final TextChannel channel = event.getChannel();
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
//
//        if (!selfVoiceState.inVoiceChannel()) {
//            event.getChannel().sendMessage("I need to be in a voice channel for this to work").queue();
//            return;
//        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (message.matches(prefixPlay) || message.equals(prefixStop) || message.equals(prefixSkip) || message.matches(prefixVolume)) {

            if (!memberVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage("You need to be in a voice channel for this command to work").queue();
                return;
            }

//            if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
//                event.getChannel().sendMessage("You need to be in the same voice channel as me for this to work").queue();
//                return;
//            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final PlayerManager playerManager = new PlayerManager();


//        if (!guildVoiceState.inVoiceChannel()) {
//            event.getMessage().getChannel().sendMessage("You need to be in the voice channel to use this command!").queue();
//            return;
//        }

            if (message.matches(prefixPlay)) {

                String[] messages = message.split(" ", 2);
                VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
                event.getGuild().getAudioManager().openAudioConnection(voiceChannel);


                playerManager.getMusicManager(guild).audioPlayer.setVolume(20);

                playerManager.loadAndPlay(event.getMessage().getTextChannel(), messages[1]);
            }

            if (message.equals(prefixStop)) {

                // GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
                musicManager.scheduler.getPlayer().stopTrack();
                musicManager.scheduler.getQueue().clear();
                event.getGuild().getAudioManager().closeAudioConnection();
                event.getMessage().getChannel().sendMessage("The player has been stopped and the queue has been cleared").queue();
            }

            //TODO не работает
            if (message.equals(prefixSkip)) {

                if (audioPlayer.getPlayingTrack() == null) {
                    channel.sendMessage("There is no track playing currently").queue();
                    return;
                }

                musicManager.scheduler.nextTrack();
                channel.sendMessage("Skipped the current track").queue();


//            GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
//            musicManager.scheduler.nextTrack();
//
//            event.getMessage().getChannel().sendMessage("Skipped").queue();
            }

            //TODO не работает
            if (message.matches(prefixVolume)) {
                String[] messages = message.split(" ", 2);

               // PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer.setVolume(Integer.parseInt(messages[1]));
                musicManager.scheduler.getPlayer().setVolume(Integer.parseInt(messages[1]));

               // audioPlayer.setVolume(Integer.parseInt(messages[1]));
//            playerManager.getGuildMusicManager(guild).player.setVolume(20);
//
//            musicManager.player.setVolume(Integer.parseInt(messages[1]));

                event.getMessage().getChannel().sendMessage("The volume is set to: " + messages[1]).queue();
            }

        }
    }
}


//            Guild guild = event.getGuild();
//            VoiceChannel channel = guild.getVoiceChannelsByName("Gaming", true).get(0);
//            AudioManager manager = guild.getAudioManager();
//
//
//            String[] messages = message.split(" ", 2);
//            PlayerManager playerManager = PlayerManager.getInstance();
//            playerManager.loadAndPlay(event.getChannel(), messages[1]);
//            playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
//            // This will get the first voice channel with the name "music"
//            // matching by voiceChannel.getName().equalsIgnoreCase("music")
//
//
//           AudioPlayerManager playerManager1 = new DefaultAudioPlayerManager();
////            AudioSourceManagers.registerRemoteSources(playerManager1);
////
//            AudioPlayer player = playerManager1.createPlayer();
//           // player.playTrack();
//            manager.setSendingHandler(new AudioPlayerSendHandler(player));
//            manager.openAudioConnection(channel);
//
////            TrackScheduler trackScheduler = new TrackScheduler(player);
////            player.addListener(trackScheduler);
//            //player.playTrack();
//
//            // MySendHandler should be your AudioSendHandler implementation
//            // Here we finally connect to the target voice channel
//            // and it will automatically start pulling the audio from the MySendHandler instance

