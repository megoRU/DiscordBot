package messages.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageMoveUser extends ListenerAdapter {

  public final String MOVE = "move";

  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();
    boolean boolPermissionAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);

    if (message.matches(MOVE)
        & !event.getMember().getUser().isBot()
        & idUser.equals("310364711587676161")
        & boolPermissionAdmin) {
      List<Member> memberList = new ArrayList<>();
      memberList.add(event.getMember());

      event.getGuild().moveVoiceMember(memberList.get(0),
          event.getGuild().getVoiceChannels().get(1))
          .queue();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
      event.getGuild().moveVoiceMember(memberList.get(0),
          event.getGuild().getVoiceChannels().get(0))
          .queue();
    }

    if (message.matches(MOVE) & !boolPermissionAdmin) {
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error: You are not Admin");
      error.setDescription("You need Permission.ADMINISTRATOR"
          + "\n-> MessageMoveUser.java");
      event.getChannel().sendMessage(error.build()).queue();
      error.clear();
    }
  }
}
