import messagesevents.ClassWithThread;
import startbot.BotStart;

public class Main {

  public static void main(String[] args) throws Exception {

    BotStart botStart = new BotStart();
    botStart.startBot();

//    List<Thread> threads = new LinkedList<>();
//    threads.add(new Thread((new ClassWithThread())));
//    threads.forEach(Thread::start);
//    for(Thread thread : threads) {
//      thread.join();
//    }
    ClassWithThread classWithThread = new ClassWithThread();
    classWithThread.start();

  }

}
