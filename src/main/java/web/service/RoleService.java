package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    //поиск роли по id
    Role searchRole(int id);

    //поиск роли по имени
    Role searchForRoleByName(String name);

    //выводим все roles
    List<Role> getAllRole();
}
