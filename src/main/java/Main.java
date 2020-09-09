import events.HelloEvents;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

  public static void main(String[] args) throws Exception  {
    JDABuilder builder = JDABuilder.createDefault("NzUzMDAxNzQ5NjUyMTc3MDI4.X1f1hw.8UPL84pkbZakPWgbtJsGAgQCBIE");
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new HelloEvents());
    builder.setActivity(Activity.playing("JS для даунов"));
    builder.build();



//    JDA jda = JDABuilder.createDefault("NzUzMDAxNzQ5NjUyMTc3MDI4.X1f1hw.8UPL84pkbZakPWgbtJsGAgQCBIE").build();
//
//    jda.addEventListener(new HelloEvents());

  }

}
