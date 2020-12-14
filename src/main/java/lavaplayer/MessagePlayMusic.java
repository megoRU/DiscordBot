package lavaplayer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessagePlayMusic extends ListenerAdapter {

    private static final String PLAY = "!play\\s.+";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String message = event.getMessage().getContentDisplay().trim();

        if (message.matches(PLAY)) {
            Guild guild = event.getGuild();

            String[] messages = message.split(" ", 2);
            VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
            event.getGuild().getAudioManager().openAudioConnection(voiceChannel);

            PlayerManager playerManager = new PlayerManager();

            playerManager.getGuildMusicManager(guild).player.setVolume(10);
            playerManager.loadAndPlay(event.getMessage().getTextChannel(), messages[1]);

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

        }

    }
}
