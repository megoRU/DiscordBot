import events.MessageWhoEnterChannel;
import events.MessageDeleting;
import events.MoveUserToChannel;
import events.YoutubeUrlWithTime;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

  public static void main(String[] args) throws Exception  {
    JDABuilder builder = JDABuilder.createDefault("NzUzMDAxNzQ5NjUyMTc3MDI4.X1f1hw.8UPL84pkbZakPWgbtJsGAgQCBIE");
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new MessageWhoEnterChannel());
    builder.addEventListeners(new MessageDeleting());
    builder.addEventListeners(new MoveUserToChannel());
    builder.addEventListeners(new YoutubeUrlWithTime());

    builder.setActivity(Activity.listening("как орёт мамка Юры"));
    builder.build();
  }
}
