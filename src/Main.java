import Models.Task;
import Service.DbContext;
import Service.ToDoListService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoListService toDoListService = new ToDoListService();
        DbContext dbContext = new DbContext();
        var connection = dbContext.connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        if (connection != null) {

            boolean exit = false;
            while (!exit) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        listAllTasks(toDoListService, connection);
                        break;
                    case 2:
                        System.out.print("Enter task description: ");
                        String newTaskDescription = scanner.nextLine();
                        Task newTask = new Task(newTaskDescription);
                        toDoListService.insertTask(connection, newTask);
                        System.out.println("Task added successfully!");
                        break;
                    case 3:
                        System.out.print("Enter the task ID to update: ");
                        int taskIdToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter the updated task description: ");
                        String updatedTaskDescription = scanner.nextLine();
                        Task updatedTask = new Task(taskIdToUpdate, updatedTaskDescription);
                        toDoListService.updateTask(connection, updatedTask);
                        break;
                    case 4:
                        System.out.print("Enter the task ID to delete: ");
                        int taskIdToDelete = scanner.nextInt();
                        scanner.nextLine();
                        toDoListService.deleteTask(connection, taskIdToDelete);
                        break;
                    case 5:
                        System.out.print("Enter status: ");
                        boolean status = scanner.nextBoolean();
                        listTasksWithStatus(toDoListService, connection, status);
                        break;
                    case 6:
                        System.out.println("Exiting the application. Goodbye!");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        }


    }

    private static void listAllTasks(ToDoListService toDoListService, Connection connection) {
        List<Task> tasks = toDoListService.listTasks(connection);
        System.out.println("Tasks:");
        for (Task task : tasks) {
            System.out.println(task.getId() + ". " + task.getDescription());
        }
    }

    private static void printMenu() {
        System.out.println("\n===== ToDo List Application Menu =====");
        System.out.println("1. List all tasks");
        System.out.println("2. Add a new task");
        System.out.println("3. Update a task");
        System.out.println("4. Delete a task");
        System.out.println("5. List tasks by status");
        System.out.println("6. Exit");
    }


    private static void listTasksWithStatus(ToDoListService toDoListService, Connection connection, boolean isDone) {
        List<Task> tasks = toDoListService.findTasksWithStatus(connection, isDone);
        for (Task task : tasks) {
            System.out.println(task.getId() + ". " + task.getDescription());
        }
    }
}