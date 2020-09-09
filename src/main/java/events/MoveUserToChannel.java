package events;

import java.util.HashMap;
import java.util.List;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.WidgetUtil.Widget;
import org.jetbrains.annotations.NotNull;

public class MoveUserToChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event)  {
    Member member = event.getMember();
    member.getUser();
    String id = member.getUser().getId();
    //VoiceChannel voiceChannel = new Widget.VoiceChannel();
//        VoiceChannel voicechannel = null;
    //473785064325513237
   // System.out.println(event.getChannelJoined().getIdLong());

//    List<VoiceChannel> voiceChannelList = new VoiceChannel();
//
   // event.getGuild().moveVoiceMember(member, event.getGuild().getVoiceChannels());

//    String user = event.getMember().getUser().getName();
//
//    member.getUser()

   // event.getGuild().getVoiceChannels();

    String idUser = event.getMember().getUser().getId();

//    if (idUser.equals("250699265389625347")) {
//      event.getGuild().moveVoiceMember(event.getMember(),
//          event.getGuild().getVoiceChannels().get(0))
//          .queue();
//    }

//    if (idUser.equals("250699265389625347")) {
//      event.getGuild().moveVoiceMember(event.getMember(),
//          event.getGuild().
//          .queue();
//    }


//    VoiceChannel vc = event.getChannelJoined();
//    Guild g = event.getGuild();
//
//      VoiceChannel nvc = (VoiceChannel) g.getVoiceChannelsByName("\uD83C\uDF10 Для всех", false);
//
//      System.out.println(nvc);
//
//      nvc.getGuild().moveVoiceMember(event.getMember(), nvc).queue();

//      g.modifyVoiceChannelPositions().selectPosition(nvc).moveTo(vc.getPosition() + 1).queue();
//      g.modifyVoiceChannelPositions().selectPosition(nvc).moveTo(vc.getPosition() + 1).queue();
//      g.moveVoiceMember(event.getMember(), nvc).queue();



//    //member.setVoiceChannel(channel)
//
//    GuildController controller = new GuildController();
//    VoiceChannel voicechannel = null;
//
//
//    for(VoiceChannel channel : event.getGuild().getVoiceChannels())
//    {
//      if(channel.getName().equalsIgnoreCase("\uD83C\uDF10 Для всех"))
//      {
//        voicechannel = channel;
//        break;
//      }
//    }
//
//    try
//    {
//      controller.moveVoiceMember((Member) event.getMessage().getMentionedUsers().get(0), voicechannel);
//    }
//    catch(Exception ex)
//    {
//      ex.printStackTrace();
//    }
//
//
//
//    // event.getMember().getUser().
//  //  event.getMember().getGuild();
//    OnlineStatus test = event.getMember().getOnlineStatus();
//    System.out.println(test);
  }
}
