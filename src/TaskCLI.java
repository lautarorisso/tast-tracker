public class TaskCLI {
    public static void main(String[] args) {

        TaskRepository repo = new TaskRepository();

        if (args.length == 0) {
            System.out.println("Please provide a command.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Please provide a description.");
                    return;
                }
                repo.addTask(args[1]);
                break;

            case "list":
                if (args.length == 1) {
                    repo.listTasks();
                    return;
                }
                try {
                    String filter = args[1].toUpperCase().replace("-", "_");
                    Status status = Status.valueOf(filter);
                    repo.listTasksByStatus(status);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status filter. Use TODO, IN_PROGRESS, or DONE.");
                }

                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Please provide an ID and a new description.");
                    return;
                }

                repo.updateTask(Integer.parseInt(args[1]), args[2]);

                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Please provide an ID.");
                    return;
                }
                repo.deleteTask(Integer.parseInt(args[1]));

                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Please provide an ID.");
                    return;
                }

                repo.markInProgress(Integer.parseInt(args[1]));

                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Please provide an ID.");
                    return;
                }

                repo.markDone(Integer.parseInt(args[1]));

                break;

            default:
                System.out.println("Unknown command");
        }
    }

}
