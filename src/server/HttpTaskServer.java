package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import manager.CreateManagers;
import manager.InMemoryTaskManager;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    HttpServer httpServer;
    private Gson gson;

    private TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = CreateManagers.getGson();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PriorityzedHandler(taskManager, gson));
    }

    public void start() {
        System.out.println("Сервер запущен на порту " + PORT);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Сервер остановлен на порту " + PORT);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer(new InMemoryTaskManager());
        httpTaskServer.start();
//        httpTaskServer.stop();
    }
}

