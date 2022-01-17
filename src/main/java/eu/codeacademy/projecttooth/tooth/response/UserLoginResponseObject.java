package eu.codeacademy.projecttooth.tooth.response;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponseObject {
    private UserLoginResponseBody userLoginResponseBody;
    private Map<String, String > tokens;
}
