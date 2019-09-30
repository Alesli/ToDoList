package by.epam.todolist.dao;

import by.epam.todolist.entity.Task;

import java.util.List;

public interface TaskDao {

    Task findTaskById(Long taskId);

    List<Task> findAll(String day);

    List<Task> taskFindAllMissed();

    Task save(Task task);

    Task update(Task task);

    Task markAsDeleted(Long taskId, boolean isDeleted);

    List<Task> findAllFromBasket();

    Task markAsCompleted(Long taskId);

    List<Task> findAllCompletedTasks();

    boolean remove(Long taskId);

    boolean removeAll();

//    ---- attachment ----

    Task findAttachmentById(Long taskId);

    Task saveAttachment(Task task);

    boolean deleteAttachment(Long taskId);
}
