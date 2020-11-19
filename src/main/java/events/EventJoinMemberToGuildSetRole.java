package events;

import db.DataBase;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventJoinMemberToGuildSetRole extends ListenerAdapter {

  @Override
  public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
    try {
      DataBase dataBase = new DataBase();
      String idGuild = event.getGuild().getId();
      String idEnterUser = event.getMember().getId();
      String nameEnterUser = event.getMember().getUser().getName();
      String userFromDB = String.valueOf(dataBase.getUserId(idEnterUser, idGuild));
      if (!userFromDB.contains(idEnterUser)) {
        dataBase.createTableForGuild(idEnterUser, nameEnterUser, idGuild);
      }
      if (userFromDB.contains(idEnterUser)) {
        System.out.println("Ничего не делать");
      }
      setJoinRole(event.getMember());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void setJoinRole(Member member) {
    Guild guild = member.getGuild();
    List<Role> roles = new ArrayList<>(member.getRoles()); // modifiable copy
    List<Role> modRoles = guild.getRolesByName("DefaultRole", true); // get roles with name "moderator"
    roles.addAll(modRoles); // add new roles
    // update the member with new roles
    guild.modifyMemberRoles(member, roles).queue();
  }
}
