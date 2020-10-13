package events;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SetRole extends ListenerAdapter {

  @Override
  public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
    setJoinRole(event.getMember());
  }

  public static void setJoinRole(Member member) {
    Guild guild = member.getGuild();
    List<Role> roles = new ArrayList<>(member.getRoles()); // modifiable copy
    List<Role> modRoles = guild.getRolesByName("Пользователи", true); // get roles with name "moderator"
    roles.addAll(modRoles); // add new roles
    // update the member with new roles
    guild.modifyMemberRoles(member, roles).queue();
  }
}
