package com.lambdanum.smsbackend.actions;

import com.lambdanum.smsbackend.actions.exceptions.TodoListException;
import com.lambdanum.smsbackend.actions.exceptions.UnimplementedCommandException;
import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.CommandHandler;
import com.lambdanum.smsbackend.command.CommandListener;
import com.lambdanum.smsbackend.command.Conversational;
import com.lambdanum.smsbackend.database.TodoListDAO;
import com.lambdanum.smsbackend.model.TodoItem;
import com.lambdanum.smsbackend.model.TodoList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@CommandListener
public class TodoLists {

    private TodoListDAO todoListDAO;

    @Autowired
    public TodoLists(TodoListDAO todoListDAO) {
        this.todoListDAO = todoListDAO;
    }

    @CommandHandler(value = "creat todo list <str> ...")
    public void createTodoList(CommandContext context, List<String> listName) {
        TodoList todoList = new TodoList();
        todoList.setName(StringUtils.join(listName," "));
        todoList.setUserId(context.getUser().getId());
        todoListDAO.persist(todoList);
        context.reply(String.format("Great! I will create '%s' list for you.",listName));
    }

    @CommandHandler(value = "my todo list")
    public void getUserTodoLists(CommandContext context) {
        List<TodoList> todoLists = todoListDAO.getTodoListsForUser(context.getUser().getId());
        if (todoLists.isEmpty()) {
            context.reply("Oops! Looks like there is nothing here!");
            context.reply("Do you want me to create a new list now?");
        } else {
            context.reply(String.format("You have %d todo lists:",todoLists.size()));
            String formattedLists = "";
            for (TodoList list : todoLists) {
                formattedLists += list.getName() + "\n";
            }
            context.reply(formattedLists);
        }
    }

    @Conversational("getUserTodoLists")
    @CommandHandler("yes")
    public void createConversationalList(CommandContext context) {
        createTodoList(context, Arrays.asList("testList1"));
    }

    @CommandHandler(value = "add <str> ... to <str> ... list")
    public void addItemToList(CommandContext context, List<String> itemName, List<String> listName) {
        try {
            TodoList list = getListByName(context, listName);
            TodoItem item = new TodoItem();
            item.setContent(StringUtils.join(itemName, " "));
            list.addItem(item);
            todoListDAO.persist(list);
            context.reply(String.format("Okay, I have added '%s' to the list!",StringUtils.join(itemName, " ")));
        } catch (TodoListException ignored) {}
    }


    @Conversational("addItemToList")
    @CommandHandler("yes")
    public void createNonExistentList(CommandContext context) {
        throw new UnimplementedCommandException("createNonExistentList");
    }

    @CommandHandler("show todo list <str> ...")
    public void showList(CommandContext context, List<String> listName) {
        try {
            TodoList list = getListByName(context, listName);
            if (list.getItems() == null) {
                context.reply("Looks like this list is empty!");
                context.reply("You can add items to it by saying something like 'Please add buying catfood to groceries list.'");
                return;
            }
            context.reply(String.format("Here is '%s'", StringUtils.join(listName, " ")));
            for (TodoItem item : list.getItems()) {
                context.reply(item.getContent());
            }
        } catch (TodoListException ignored) {}
    }

    private TodoList getListByName(CommandContext context, List<String> listName) {
        List<TodoList> todoLists = todoListDAO.getTodoListForUserById(context.getUser().getId(), StringUtils.join(listName, " "));
        if (todoLists.isEmpty()) {
            context.reply("I was not able to find that list. Do you want me to create it now?");
        }
        if (todoLists.size() == 1) {
            return todoLists.get(0);
        }
        throw new TodoListException();
    }

    @CommandHandler("delet list <str> ...")
    public void deleteList(CommandContext context, List<String> listName) {
        throw new UnimplementedCommandException("deleteList");
    }
}
