package java.yandex.practicum;

import manager.CreateManagers;
import manager.TaskManager;
import model.Epic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {

    @Test
    public void shouldAddNewEpic() {
        TaskManager taskManager = CreateManagers.getDefaultTask();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpicDescription");
        final int epicid = taskManager.addEpic(epic).getid();

        final Epic savedEpic = taskManager.getEpicByid(epicid);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst(), "Задачи не совпадают.");
    }

    @Test
    public void checkThatClassInstancesAreEqualToEachOther() {
        Epic epic1 = new Epic("Test addNewEpic", "Test addNewEpic description");
        Epic epic2 = new Epic("Test addNewEpic", "Test addNewEpic description");
        epic1.setid(0);
        epic2.setid(0);
        assertEquals(epic1, epic2);
    }


}