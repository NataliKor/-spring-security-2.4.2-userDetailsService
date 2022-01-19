package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    //поиск роли по id
    Role searchRole(int id);

    //поиск роли по имени
    Role searchForRoleByName(String name);

    //выводим все roles
    List<Role> getAllRole();
}
