package by.epam.todolist.service;

import by.epam.todolist.entity.Attachment;
import by.epam.todolist.entity.Task;
import com.sun.jersey.multipart.FormDataBodyPart;

import java.io.InputStream;
import java.util.List;

public interface TaskService {

    Task findTaskById(Long taskId);

    List<Task> findAllByDay(String day);

    List<Task> taskFindAllMissed();

    Task saveTask(Task task);

    Task markTaskAsDeleted(Long taskId, boolean isDeleted);

    List<Task> findAllFromBasket();

    Task markAsCompleted(Long taskId);

    List<Task> findAllCompletedTasks();

    boolean removeTask(Long taskId);

    boolean removeAll();

//    ---- attachment ----

    Attachment saveFile(InputStream inputStream, FormDataBodyPart fileDetail);

    Task findAttachmentById(Long taskId);

    Task saveAttachment(Task task);

    void deleteAttachment(Long taskId);
}
