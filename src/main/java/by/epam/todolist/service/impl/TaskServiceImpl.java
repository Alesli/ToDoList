package by.epam.todolist.service.impl;

import by.epam.todolist.dao.TaskDao;
import by.epam.todolist.dao.impl.TaskDaoImpl;
import by.epam.todolist.entity.Attachment;
import by.epam.todolist.entity.Task;
import by.epam.todolist.service.TaskService;
import by.epam.todolist.util.FileUtil;
import com.sun.jersey.multipart.FormDataBodyPart;

import java.io.InputStream;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao = new TaskDaoImpl();


    @Override
    public Task findTaskById(Long taskId) {
        return taskDao.findTaskById(taskId);
    }

    @Override
    public List<Task> findAllByDay(String day) {
        return taskDao.findAll(day);
    }

    @Override
    public List<Task> taskFindAllMissed() {
        return taskDao.taskFindAllMissed();
    }

    @Override
    public Task saveTask(Task task) {
        if (task.getId() == null) {
            task.setDeleted(false);
            task.setCompleted(false);
        }
        return taskDao.save(task);
    }

    @Override
    public Task markTaskAsDeleted(Long taskId, boolean isDeleted) {
        return taskDao.markAsDeleted(taskId, isDeleted);
    }

    @Override
    public List<Task> findAllFromBasket() {
        return taskDao.findAllFromBasket();
    }

    @Override
    public Task markAsCompleted(Long taskId) {
        return taskDao.markAsCompleted(taskId);
    }

    @Override
    public List<Task> findAllCompletedTasks() {
        return taskDao.findAllCompletedTasks();
    }

    @Override
    public boolean removeTask(Long taskId) {
        return taskDao.remove(taskId);
    }

    //todo: удалить у всех заданий файлы на сервере
    @Override
    public boolean removeAll() {
        return taskDao.removeAll();
    }

    //    ---- attachment ----

    /**
     * сохраняем файл физически
     */
    @Override
    public Attachment saveFile(InputStream inputStream, FormDataBodyPart fileDetail) {
        return FileUtil.saveFile(inputStream, fileDetail);
    }

    @Override
    public Task findAttachmentById(Long taskId) {
        return taskDao.findAttachmentById(taskId);
    }

    @Override
    public Task saveAttachment(Task task) {
        return taskDao.saveAttachment(task);
    }

    @Override
    public void deleteAttachment(Long taskId) {
        Task attachment = findAttachmentById(taskId);
        if (FileUtil.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
            taskDao.deleteAttachment(taskId);
        }
    }
}