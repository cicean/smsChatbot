Command definition
==================
```java
@CommandListener
public class SayHelloCommand {

    @CommandHandler(command = "say hello <int> time")
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

    @CommandHandler(command = "drop table <str>", requiredRole = UserRoleEnum.ADMIN)
    public void dropTable(String name) {
        System.out.println("WARNING: Dropping table !");
    }

    //This command can only be executed after "execute" has been called.
    @Conversational("execute")
    @CommandHandler(command = "one more time") {
        System.out.println("Hello");
    }
}

```
