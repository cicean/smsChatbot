Command definition
==================
```
@CommandListener
public class SayHelloCommand {

    @CommandHandler(command = "say hello <int> times")
    public void execute(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Hello");
        }
    }

    @CommandHandler(command = "say <str> hello <int>")
    public void namedHello(String name, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Hello " + name + "!");
        }
    }
}

```