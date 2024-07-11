import com.google.gson.Gson;
import server.HttpTaskServer;
import manager.CreateManagers;
import manager.TaskManager;
import manager.TaskNotFoundException;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты сервера")
public class HttpTaskServerTest {

    Gson gson = CreateManagers.getGson();
    TaskManager taskManager;
    HttpTaskServer taskServer;
    HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void init() throws IOException {
        taskManager = CreateManagers.getDefaultTask();
        taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
    }

    @AfterEach
    void afterEach() {
        taskServer.stop();
    }

    @Test
    public void shouldCreateTask() throws IOException, InterruptedException, TaskNotFoundException {
        Task expectedTask = new Task("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15));
        URI url = URI.create("http://localhost:8080/tasks");
        String body = gson.toJson(expectedTask);

        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        expectedTask.setid(1);
        assertEquals(201, response.statusCode());
        assertEquals(expectedTask, taskManager.getTaskByid(1));

    }

    @Test
    public void shouldCorrectlyReturnTaskById() throws IOException, InterruptedException {
        Task createdTask = new Task("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15));
        URI url = URI.create("http://localhost:8080/tasks/1");
        taskManager.addTask(createdTask);
        String expectedResponse = gson.toJson(createdTask);

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(expectedResponse, response.body());
    }

    //
    @Test
    public void shouldCorrectlyReturnListOfTasks() throws IOException, InterruptedException {
        Task createdTask = new Task("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15));
        Task createdTask2 = new Task("Task", "Description",
                LocalDateTime.of(2025, 12, 12, 12, 12), Duration.ofMinutes(15));
        URI url = URI.create("http://localhost:8080/tasks");
        taskManager.addTask(createdTask);
        taskManager.addTask(createdTask2);
        String expectedResponse = gson.toJson(taskManager.getAllTasks());

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(expectedResponse, response.body());
    }

    @Test
    public void shouldCorrectlyDeleteListOfTasks() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        taskManager.addTask(task);

        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).method("DELETE", HttpRequest.BodyPublishers.ofString("tasks")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        List<Task> tasks = taskManager.getAllTasks();

        assertEquals(tasks.size(), 0, "Задачи не удалена");
    }

    @Test
    public void shouldCorrectlyUpdateTask() throws IOException, InterruptedException, TaskNotFoundException {
        Task createdTask = new Task("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15));
        Task updatedTask = new Task("Task", "Description",
                LocalDateTime.of(2025, 12, 12, 12, 12), Duration.ofMinutes(15));
        URI url = URI.create("http://localhost:8080/tasks/1");

        taskManager.addTask(createdTask);
        updatedTask.setid(1);

        String body = gson.toJson(updatedTask);

        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        assertEquals(updatedTask, taskManager.getTaskByid(1));
    }

    @Test
    public void shouldCreateSubtask() throws IOException, InterruptedException, TaskNotFoundException {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask expectedSubTask = new Subtask("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        URI url = URI.create("http://localhost:8080/subtasks");
        String body = gson.toJson(expectedSubTask);

        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        expectedSubTask.setid(2);

        assertEquals(expectedSubTask, taskManager.getSubTaskByid(2));
    }

    @Test
    public void shouldCorrectyUpdateSubtask() throws IOException, InterruptedException, TaskNotFoundException {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask createdSub = new Subtask("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        Subtask updatedSub = new Subtask("Task", "Description",
                LocalDateTime.of(2025, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        URI url = URI.create("http://localhost:8080/tasks/1");

        taskManager.addSubtask(createdSub);
        updatedSub.setid(2);

        String body = gson.toJson(updatedSub);

        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(updatedSub, taskManager.getSubTaskByid(2));
    }

    @Test
    public void shouldCorrectlyReturnSubtaskById() throws IOException, InterruptedException, TaskNotFoundException {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask expectedSubTask = new Subtask("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        taskManager.addSubtask(expectedSubTask);

        URI url = URI.create("http://localhost:8080/subtasks/2");
        String expected = gson.toJson(expectedSubTask);

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(expected, response.body());

    }

    @Test
    public void shouldCorrectlyReturnSubtasksMap() throws IOException, InterruptedException, TaskNotFoundException {

        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask subTask1 = new Subtask("Task", "Description",
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        Subtask subTask2 = new Subtask("Task", "Description",
                LocalDateTime.of(2025, 12, 12, 12, 12), Duration.ofMinutes(15), 1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        URI url = URI.create("http://localhost:8080/subtasks");
        String expected = gson.toJson(taskManager.getAllSubtask());

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(expected, response.body());


    }

    @Test
    public void shouldCreateEpic() throws IOException, InterruptedException, TaskNotFoundException {
        Epic expectedEpic = new Epic("Epic", "Description");
        URI url = URI.create("http://localhost:8080/epics");
        String body = gson.toJson(expectedEpic);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).method("POST", HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        assertEquals(201, response.statusCode());
        expectedEpic.setid(1);

        assertEquals(expectedEpic, taskManager.getEpicByid(1));
    }

    @Test
    public void shouldCorrectlyReturnEpicById() throws IOException, InterruptedException {
        Epic expectedEpic = new Epic("Epic", "Description");
        URI url = URI.create("http://localhost:8080/epics/1");
        taskManager.addEpic(expectedEpic);
        String expectedResponse = gson.toJson(expectedEpic);

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(expectedResponse, response.body());
    }

    @Test
    public void shouldCorrectlyReturnEpicsMap() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic1", "Description1");
        Epic epic2 = new Epic("Epic2", "Description2");
        URI url = URI.create("http://localhost:8080/epics");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        String expectedResponse = gson.toJson(taskManager.getAllEpics());

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(expectedResponse, response.body());
    }

    @Test
    public void shouldReturnMethodNotAllowedtOnWrongRequestMethod() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest.newBuilder().uri(url).PUT(HttpRequest.BodyPublishers.ofString("Test")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());

    }
}
