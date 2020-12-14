package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MessagePlayMusic extends ListenerAdapter {

    private static final String PLAY = "!play\\s.+";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String message = event.getMessage().getContentDisplay().trim();

        if (message.matches(PLAY)) {
            String[] messages = message.split(" ", 2);
            PlayerManager playerManager = PlayerManager.getInstance();
            playerManager.loadAndPlay(event.getChannel(), messages[1]);
            playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
            Guild guild = event.getGuild();
            // This will get the first voice channel with the name "music"
            // matching by voiceChannel.getName().equalsIgnoreCase("music")
            VoiceChannel channel = guild.getVoiceChannelsByName("Gaming", true).get(0);
            AudioManager manager = guild.getAudioManager();

            AudioPlayerManager playerManager1 = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager1);
            AudioPlayer player = playerManager1.createPlayer();

            // MySendHandler should be your AudioSendHandler implementation
            manager.setSendingHandler(new AudioPlayerSendHandler(player));
            // Here we finally connect to the target voice channel
            // and it will automatically start pulling the audio from the MySendHandler instance
            manager.openAudioConnection(channel);

        }

    }
}
