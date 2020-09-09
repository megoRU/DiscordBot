package events;

import java.util.ArrayList;
import java.util.HashMap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MoveUserToChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {

    Member member = event.getChannelJoined().getGuild().getMember(event.getMember().getUser());


    HashMap<Integer, Member> users = new HashMap<Integer, Member>();
    users.put(1, member);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    event.getChannelJoined()
        .getGuild().moveVoiceMember(users.get(1), event.getGuild().getVoiceChannels().get(1))
       .queue();

    try {
      String idUser = event.getMember().getUser().getId();
      if (!event.getMember().getUser().isBot() & !idUser.equals("") //250699265389625347
          & !idUser.equals("580388852448100355") & !idUser.equals("335466800793911298")) {
        while (true) {
          // List<Member> memberList = new ArrayList<>();
          users.put(1, event.getMember());
          event.getChannelJoined().getGuild().moveVoiceMember(users.get(1), event.getGuild().getVoiceChannels().get(1))
              .queue();

//          event.getGuild().moveVoiceMember(users.get(1),
//              event.getGuild().getVoiceChannels().get(1))
//              .queue();

          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          event.getGuild().moveVoiceMember(users.get(1),
              event.getGuild().getVoiceChannels().get(0))
              .queue();
          users.remove(1);
          //Thread.sleep(1800000);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
