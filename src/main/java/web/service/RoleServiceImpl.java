package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;

    //поиск роли по id
    @Override
    @Transactional
    public Role searchRole(int id) {
        return roleDao.searchRole(id);
    }

    //поиск роли по имени
    @Override
    @Transactional
    public Role searchForRoleByName(String name) {
        return roleDao.searchForRoleByName(name);
    }

    //выводим все roles
    @Override
    @Transactional
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }
}
