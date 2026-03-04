import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TaskRepository {
    private static final String FILE_NAME = "tasks.json";
    private List<Task> tasks;
    private int nextId = 1;

    public TaskRepository() {
        tasks = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            Path path = Paths.get(FILE_NAME);
            if (!Files.exists(path)) {
                Files.write(path, "[]".getBytes());
            }
            String content = Files.readString(path);
            if (!content.equals("[]")) {
                parseJson(content);
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }

    public void parseJson(String content) {

        content = content.trim();

        if (content.equals("[]") || content.isBlank()) {
            return;
        }

        // Sacamos el [ y el ]
        content = content.substring(1, content.length() - 1);

        // Separamos cada objeto
        String[] objects = content.split("\\},\\{");

        for (String obj : objects) {

            // Limpiamos llaves sobrantes
            obj = obj.replace("{", "").replace("}", "");

            String[] fields = obj.split(",");

            int id = 0;
            String description = "";
            Status status = Status.TODO;
            LocalDateTime createdAt = null;
            LocalDateTime updatedAt = null;

            for (String field : fields) {

                String[] keyValue = field.split(":", 2);

                String key = keyValue[0].replace("\"", "");
                String value = keyValue[1].replace("\"", "");

                switch (key) {

                    case "id":
                        id = Integer.parseInt(value);
                        break;

                    case "description":
                        description = value;
                        break;

                    case "status":
                        status = Status.valueOf(value);
                        break;

                    case "createdAt":
                        createdAt = LocalDateTime.parse(value);
                        break;

                    case "updatedAt":
                        updatedAt = LocalDateTime.parse(value);
                        break;
                }
            }

            Task task = new Task(id, description, status, createdAt, updatedAt);
            tasks.add(task);

            // 🔥 Ajustar nextId correctamente
            if (id >= nextId) {
                nextId = id + 1;
            }
        }
    }

    private void saveToFile() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);

                sb.append("{");
                sb.append("\"id\":").append(t.getId()).append(",");
                sb.append("\"description\":\"").append(t.getDescription()).append("\",");
                sb.append("\"status\":\"").append(t.getStatus()).append("\",");
                sb.append("\"createdAt\":\"").append(t.getCreatedAt()).append("\",");
                sb.append("\"updatedAt\":\"").append(t.getUpdatedAt()).append("\"");
                sb.append("}");

                if (i < tasks.size() - 1) {
                    sb.append(",");
                }
            }

            sb.append("]");

            Files.write(Paths.get(FILE_NAME), sb.toString().getBytes());

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void addTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
        saveToFile();
        System.out.println("Task added succesfully (ID: " + task.getId() + ")");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void listTasksByStatus(Status status) {
        boolean found = false;

        for (Task t : tasks) {
            if (t.getStatus() == status) {
                System.out.println(t);
                found = true;
            }
            if (!found) {
                System.out.println("No tasks found with status " + status);
            }
        }
    }

    public void deleteTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) {
            saveToFile();
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
        ;
    }

    public void updateTask(int id, String newDescription) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.updateDescription(newDescription);
                saveToFile();
                System.out.println("Task updated.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void markInProgress(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.updatedStatus(Status.IN_PROGRESS);
                saveToFile();
                System.out.println("Task marked as in progress.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public void markDone(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.updatedStatus(Status.DONE);
                saveToFile();
                System.out.println("Task marked as done.");
                return;
            }
        }

        System.out.println("Task not found.");
    }
}
