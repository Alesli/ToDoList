package by.epam.todolist.entity;

import lombok.*;

import java.util.Date;

/**
 * data entity task
 *
 * @author Alesia Skaraknoh
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    private Long id;
    private String name;
    private String description;
    private Date eventDate;
    private Boolean deleted;
    private Boolean completed;

    private String originalName;
    private String generatedPath;
    private String generatedName;
    private String mimeType;

//    @Override
//    public String toString() {
//        return "Task " +
//                "id= " + id +
//                ", name= " + name +
//                ", description= " + description +
//                ", eventDate= " + eventDate +
//                ", deleted= " + deleted +
//                ", completed= " + completed +
//                ", file= " + originalName +"."+ generatedPath +"."+ generatedName +"."+ mimeType;
//    }
}
