import events.HelloEvents;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

  public static void main(String[] args) throws Exception  {
    JDA jda = JDABuilder.createDefault("NzUzMDAxNzQ5NjUyMTc3MDI4.X1f1hw.8UPL84pkbZakPWgbtJsGAgQCBIE").build();

    jda.addEventListener(new HelloEvents());

  }

}
