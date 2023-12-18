package com.meryembarkallah21.gamehub.service;

import com.meryembarkallah21.gamehub.model.Role;
import com.meryembarkallah21.gamehub.model.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();

    void createRole(Role theRole);

    void deleteRole(Long roleId);

    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);
}
