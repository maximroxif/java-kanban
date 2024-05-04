import Manager.Managers;
import Manager.TaskManager;
import Model.Epic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {

    @Test
    public void shouldAddNewEpic() {
        TaskManager taskManager = Managers.getDefaultTask();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpicDescription");
        final int epicId = taskManager.addEpic(epic).getID();

        final Epic savedEpic = taskManager.getEpicByID(epicId);

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
        epic1.setID(0);
        epic2.setID(0);
        assertEquals(epic1, epic2);
    }


}