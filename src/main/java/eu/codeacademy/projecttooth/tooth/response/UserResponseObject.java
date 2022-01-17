package eu.codeacademy.projecttooth.tooth.response;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseObject {
    private UserResponseBody userResponseBody;
    private Map<String, String > tokens;
}
