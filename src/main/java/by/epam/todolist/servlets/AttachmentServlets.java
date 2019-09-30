package by.epam.todolist.servlets;

import by.epam.todolist.entity.Attachment;
import by.epam.todolist.entity.Task;
import by.epam.todolist.service.TaskService;
import by.epam.todolist.service.impl.TaskServiceImpl;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/file")
@MultipartConfig
public class AttachmentServlets {

    private TaskService taskService = new TaskServiceImpl();

//    http://localhost:8080/api/file/saveFile/{taskId}

    /**
     * сохраняем файл на диск сервера
     * получаем обратно сведения о нем
     */
    @POST
    @Path("/saveFile/{taskId}")
    @Consumes({MediaType.MULTIPART_FORM_DATA, })
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveFile(@PathParam("taskId") Long taskId,
                             @FormDataParam("file") InputStream inputStream,
                             @FormDataParam("file") FormDataBodyPart fileDetail) {
        if (inputStream == null || fileDetail == null) {
            return Response.status(400).build();
        } else {
            Attachment result = taskService.saveFile(inputStream, fileDetail);
            Task task = taskService.findTaskById(taskId);
            task.setOriginalName(result.getOriginalName());
            task.setGeneratedPath(result.getGeneratedPath());
            task.setGeneratedName(result.getGeneratedName());
            task.setMimeType(result.getMimeType());

            Task res = taskService.saveAttachment(task);

            return res != null ?
                    Response.ok().entity(result).build() :
                    Response.noContent().build();
        }
    }

//    http://localhost:8080/api/file/removeAttachment/{taskId}
    @PUT
    @Path("/removeAttachment/{taskId}")
    public Response removeAttachment(@PathParam("taskId") Long taskId) {
        taskService.deleteAttachment(taskId);
        return Response.ok().build();
    }

//    http://localhost:8080/api/file/getFile/2
    @GET
    @Path("/getFile/{taskId}")
    public Response getFile(@PathParam("taskId") Long taskId) {
        Task result = taskService.findAttachmentById(taskId);
        File file = new File(result.getGeneratedPath() + result.getGeneratedName());
        if (file.exists()) {
            Response.ResponseBuilder responseBuilder = Response.ok(file);
            responseBuilder
                    .header("Content-Disposition", "attachment; filename=\"" + result.getOriginalName() + "\"")
                    .type(result.getMimeType());
            return responseBuilder.build();
        } else {
            return Response.noContent().build();
        }
    }

//        для скачивания ссылки на текстовый документ
//    http://localhost:8080/api/file/downloadFile/{taskId}
    @GET
    @Path("/downloadFile/{taskId}")
    public Response downloadFile(@PathParam("taskId") Long taskId) {
        Task result = taskService.findAttachmentById(taskId);

        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException

            {
                try {
                    java.nio.file.Path path = Paths.get(result.getGeneratedPath() +
                            result.getGeneratedName());
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } catch (Exception e) {
                    throw new WebApplicationException(Integer.parseInt("File Not Found !!"));
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename=" + result.getOriginalName())
                .build();
    }

}
