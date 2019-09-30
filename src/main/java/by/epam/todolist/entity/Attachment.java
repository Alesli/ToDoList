package by.epam.todolist.entity;

import lombok.*;

/**
 * data entity attachment
 *
 * @author Alesia Skaraknoh
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment {

    private String originalName;
    private String generatedPath;
    private String generatedName;
    private String mimeType;
}
