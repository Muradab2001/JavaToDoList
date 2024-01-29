package Service;

import Models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListService {
    public void insertTask(Connection connection, Task task) {
        String sql = "INSERT INTO Tasks ( description) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, task.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Task inserted successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> listTasks(Connection connection) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE isDelete = false";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String description = resultSet.getString("description");
                Task task = new Task(taskId, description);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }


    public void deleteTask(Connection connection, int taskId) {
        String sql = "UPDATE tasks SET isDelete = true WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Connection connection, Task updatedTask) {
        String sql = "UPDATE tasks SET description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedTask.getDescription());
            preparedStatement.setInt(2, updatedTask.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable(Connection connection) {
        var sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                 id SERIAL PRIMARY KEY,
                 description VARCHAR(255) NOT NULL,
                 isDelete BOOLEAN NOT NULL DEFAULT false,
                 isDone BOOLEAN NOT NULL DEFAULT false
                )""";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> findTasksWithStatus(Connection connection, boolean isDone) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE isDone = ? AND isDelete = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isDone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int taskId = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    boolean taskIsDone = resultSet.getBoolean("isDone");
                    boolean isDelete = resultSet.getBoolean("isDelete");
                    Task task = new Task(taskId, description);
                    task.setDone(taskIsDone);
                    task.setDelete(isDelete);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    public Task findTaskById(Connection connection, int taskId) {
        String sql = "SELECT * FROM tasks WHERE id = ? AND isDelete = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int foundTaskId = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    boolean isDone = resultSet.getBoolean("isDone");
                    boolean isDelete = resultSet.getBoolean("isDelete");
                    Task task = new Task(foundTaskId, description);
                    task.setDone(isDone);
                    task.setDelete(isDelete);
                    return task;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
