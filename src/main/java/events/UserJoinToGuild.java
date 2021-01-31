package events;

import db.DataBase;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UserJoinToGuild extends ListenerAdapter {

  @Override
  public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
    try {
      DataBase dataBase = new DataBase();
      String idGuild = event.getGuild().getId();
      String idEnterUser = event.getMember().getId();
      String nameEnterUser = event.getMember().getUser().getName();
      String userFromDB = String.valueOf(dataBase.getUserId(idEnterUser, idGuild));
      if (!userFromDB.equals(idEnterUser)) {
        dataBase.createDefaultUserInGuild(idEnterUser, nameEnterUser, idGuild);
      }
      //setJoinRole(event.getMember());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  public static void setJoinRole(Member member) {
//    try {
//      Guild guild = member.getGuild();
//      List<Role> roles = new ArrayList<>(member.getRoles()); // modifiable copy
//      List<Role> modRoles = guild.getRolesByName("DefaultRole", true); // get roles with name "moderator"
//      roles.addAll(modRoles); // add new roles
//      // update the member with new roles
//      guild.modifyMemberRoles(member, roles).queue();
//    }catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
}
