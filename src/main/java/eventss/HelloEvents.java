package eventss;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public interface HelloEvents extends EventListener {

  void onGuildMessageReceivedEvent(GuildMessageReceivedEvent event);
}
