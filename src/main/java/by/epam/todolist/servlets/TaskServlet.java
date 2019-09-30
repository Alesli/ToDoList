package by.epam.todolist.servlets;

import by.epam.todolist.entity.Task;
import by.epam.todolist.service.TaskService;
import by.epam.todolist.service.impl.TaskServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/task")
//@MultipartConfig
public class TaskServlet {

    private TaskService taskService = new TaskServiceImpl();

    //    http://localhost:8080/api/task/getOne/{taskId}
    @GET
    @Path("/getOne/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("taskId") Long taskId) {
        Task result = taskService.findTaskById(taskId);
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

//    http://localhost:8080/api/task/findAllByDay/{day}

    @GET
    @Path("/findAllByDay/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllByToday(@PathParam("day") String day) {
        List<Task> result = taskService.findAllByDay(day);
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/taskFindAllMissed
    @GET
    @Path("/taskFindAllMissed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response taskFindAllMissed() {
        List<Task> result = taskService.taskFindAllMissed();
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    /*
   POST http://localhost:8080/api/task/save
   {
        "name":"reading",
       "description":"read magazine",
       "eventDate":"2019-09-08"
   }
   */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Task task) {
        Task result = taskService.saveTask(task);
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/markAsDeleted/{taskId}/true - помещаем в корзину

    //    http://localhost:8080/api/task/markAsDeleted/{taskId}/false - возвращаем таск и он восстанавливается в пункте Today
    @GET
    @Path("/markAsDeleted/{taskId}/{isDeleted}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAsDeleted(@PathParam("taskId") Long taskId,
                                  @PathParam("isDeleted") boolean isDeleted) {
        Task result = taskService.markTaskAsDeleted(taskId, isDeleted);
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/findAllFromBasket
    @GET
    @Path("/findAllFromBasket")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllFromBasket() {
        List<Task> result = taskService.findAllFromBasket();
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/markAsCompleted/{taskId}
    @GET
    @Path("/markAsCompleted/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAsCompleted(@PathParam("taskId") Long taskId) {
        Task result = taskService.markAsCompleted(taskId);
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/findAllCompletedTasks
    @GET
    @Path("/findAllCompletedTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCompletedTasks() {
        List<Task> result = taskService.findAllCompletedTasks();
        return result != null ?
                Response.ok().entity(result).build() :
                Response.noContent().build();
    }

    //    http://localhost:8080/api/task/remove/{taskId}
    @DELETE
    @Path("/remove/{taskId}")
    public Response remove(@PathParam("taskId") Long taskId) {
        taskService.removeTask(taskId);
        return Response.ok().build();
    }

    //    http://localhost:8080/api/task/removeAll
    @DELETE
    @Path("/removeAll")
    public Response removeAll() {
        taskService.removeAll();
        return Response.ok().build();
    }
}
