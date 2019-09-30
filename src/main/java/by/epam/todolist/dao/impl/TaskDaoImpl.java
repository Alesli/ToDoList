package by.epam.todolist.dao.impl;

import by.epam.todolist.dao.TaskDao;
import by.epam.todolist.entity.Task;
import by.epam.todolist.jdbc.MySqlConnector;
import by.epam.todolist.jdbc.MySqlQuery;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The class implements EmployeeDao interface, retrieves data from MySQL database tables
 * through requests from the mysql_queries.property file using JDBC.
 *
 * @author Alesia Skarakhod
 */
public class TaskDaoImpl implements TaskDao {

    private Connection connection = MySqlConnector.getInstance().getConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public Task findTaskById(Long taskId) {
        Task task = new Task();
        try {
            String query = MySqlQuery.getInstance().getQuery("findTaskById");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, taskId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = getTask(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, resultSet);
        }
        return task;
    }

    @Override
    public List<Task> findAll(String day) {
        String query;
        List<Task> taskList = new ArrayList<>();

        if (day.equalsIgnoreCase("today") || day.equalsIgnoreCase("tomorrow")) {
            query = MySqlQuery.getInstance().getQuery("taskFindAllByDay");
        } else if (day.equalsIgnoreCase("someday")) {
            query = MySqlQuery.getInstance().getQuery("taskFindAllSomeday");
        } else {
            return new ArrayList<>();
        }
        Date date = day.equalsIgnoreCase("today") ? getTime(0) : getTime(86400000);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = getTask(resultSet);
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, resultSet);
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<Task> taskFindAllMissed() {
        List<Task> taskList = new ArrayList<>();
        try {
            String query = MySqlQuery.getInstance().getQuery("taskFindAllMissed");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = getTask(resultSet);
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, resultSet);
        }
        return new ArrayList<>(0);
    }

    @Override
    public Task save(Task task) {
        String query = MySqlQuery.getInstance().getQuery("taskInsert");
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(task.getEventDate().getTime()));
            preparedStatement.setString(4, task.getOriginalName());
            preparedStatement.setString(5, task.getGeneratedPath());
            preparedStatement.setString(6, task.getGeneratedName());
            preparedStatement.setString(7, task.getMimeType());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                task.setId(resultSet.getLong(1));
                return task;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public Task update(Task task) {
        String query = MySqlQuery.getInstance().getQuery("taskUpdate");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(task.getEventDate().getTime()));
            preparedStatement.setBoolean(4, task.getDeleted());
            preparedStatement.setBoolean(5, task.getCompleted());
            if (task.getId() != null) {
                preparedStatement.setLong(6, task.getId());
                return preparedStatement.executeUpdate() > 0 ? task : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public Task markAsDeleted(Long taskId, boolean isDeleted) {
        Task task = findTaskById(taskId);
        task.setEventDate(isDeleted ? task.getEventDate() : getTime(0));
        task.setDeleted(isDeleted);
        return update(task);
    }

    @Override
    public Task markAsCompleted(Long taskId) {
        Task task = findTaskById(taskId);
        task.setCompleted(true);
        return update(task);
    }

    /**
     * The method gets all completed tasks with completed = 1 and deleted = 0
     *
     * @return method findTaskByParam
     */
    @Override
    public List<Task> findAllCompletedTasks() {
        String query = MySqlQuery.getInstance().getQuery("taskFindAllCompleted");
        return findTaskByParam(query);
    }

    /**
     * The method gets all task from basket with deleted = 1
     *
     * @return method findTaskByParam
     */
    @Override
    public List<Task> findAllFromBasket() {
        String query = MySqlQuery.getInstance().getQuery("taskFindAllBasket");
        return findTaskByParam(query);
    }

    private List<Task> findTaskByParam(String query) {
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<Task> taskList = new ArrayList<>();
            while (resultSet.next()) {
                Task task = getTask(resultSet);
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, resultSet);
        }
        return new ArrayList<>(0);
    }

    @Override
    public boolean remove(Long taskId) {
        try {
            String query = MySqlQuery.getInstance().getQuery("taskDelete");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, taskId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, null);
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        String query = MySqlQuery.getInstance().getQuery("allDelete");
        try {
            preparedStatement = connection.prepareStatement(query);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, null);
        }
        return false;
    }

    //    ---- attachment ----
    @Override
    public Task findAttachmentById(Long taskId) {
        Task task = new Task();
        try {
            String query = MySqlQuery.getInstance().getQuery("attachmentGetOne");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, taskId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = getAttachment(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(preparedStatement, resultSet);
        }
        return task;
    }

    @Override
    public Task saveAttachment(Task task) {
        String query = MySqlQuery.getInstance().getQuery("attachmentUpdate");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, task.getOriginalName());
            preparedStatement.setString(2, task.getGeneratedPath());
            preparedStatement.setString(3, task.getGeneratedName());
            preparedStatement.setString(4, task.getMimeType());
            preparedStatement.setLong(5, task.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return findTaskById(task.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public boolean deleteAttachment(Long taskId) {
        String query = MySqlQuery.getInstance().getQuery("attachmentDelete");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, taskId);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, resultSet);
        }
        return false;
    }

    private Task getTask(ResultSet resultSet) {
        try {
            return Task
                    .builder()
                    .id(resultSet.getLong(1))
                    .name(resultSet.getString(2))
                    .description(resultSet.getString(3))
                    .eventDate(new java.util.Date(resultSet.getDate(4).getTime()))
                    .deleted(resultSet.getBoolean(5))
                    .completed(resultSet.getBoolean(6))
                    .originalName(resultSet.getString(7))
                    .generatedPath(resultSet.getString(8))
                    .generatedName(resultSet.getString(9))
                    .mimeType(resultSet.getString(10))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Task getAttachment(ResultSet resultSet) {
        try {
            return Task
                    .builder()
                    .id(resultSet.getLong(1))
                    .originalName(resultSet.getString(2))
                    .generatedPath(resultSet.getString(3))
                    .generatedName(resultSet.getString(4))
                    .mimeType(resultSet.getString(5))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(PreparedStatement preparedStatement, ResultSet resultSet) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Date getTime(long value) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Date(dateFormat.parse(dateFormat.format(new java.util.Date())).getTime() + value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}