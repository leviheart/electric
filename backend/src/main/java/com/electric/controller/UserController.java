package com.electric.controller;

import com.electric.dto.UserInfo;
import com.electric.model.ApiResponse;
import com.electric.model.User;
import com.electric.service.AuthService;
import com.electric.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserInfo>>> getAllUsers() {
        List<UserInfo> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfo>> getUserById(@PathVariable Long id) {
        UserInfo user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfo>> updateUser(
            @PathVariable Long id,
            @RequestBody UserService.UpdateUserRequest request) {
        UserInfo user = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success("用户更新成功", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("用户删除成功", null));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request.oldPassword, request.newPassword);
        return ResponseEntity.ok(ApiResponse.success("密码修改成功", null));
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<Void>> toggleEnable(
            @PathVariable Long id,
            @RequestBody EnableRequest request) {
        userService.toggleEnable(id, request.enabled);
        return ResponseEntity.ok(ApiResponse.success(request.enabled ? "用户已启用" : "用户已禁用", null));
    }

    public static class ChangePasswordRequest {
        public String oldPassword;
        public String newPassword;
    }

    public static class EnableRequest {
        public boolean enabled;
    }
}
