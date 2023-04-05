package com.example.demo_security.pojo;

import com.example.demo_security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 10/03/2023 - 15:18
 * @project demo_security
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String email;

    public static UserResponse mapFromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public static List<UserResponse> mapFromUser(List<User> users) {
        return users.stream()
                .map(UserResponse::mapFromUser)
                .toList();
    }
}
