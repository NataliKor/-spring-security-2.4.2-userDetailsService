package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //выводим всех users
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    //добавляем user
    @Override
    @Transactional
    public void addUser(String name, String lastName, int age, String password, Set<Role> roles) {
        for (Role role : roles) {
            if (role.getId() == 0) {
                if (role.getName().equals("ADMIN")) {
                    role.setId(1);
                }
                if (role.getName().equals("USER")) {
                    role.setId(2);
                }
            }
        }
        userDao.addUser(name, lastName, age, password, roles);
    }

    //поиск пользователя по id
    @Override
    @Transactional
    public User searchUser(int id) {
        return userDao.searchUser(id);
    }

    //изменяем user
    @Override
//    @Transactional
    public void updateUser(int id, User user) {
        for (Role role : user.getRoles()) {
            if (role.getId() == 0) {
                if (role.getName().equals("ADMIN")) {
                    role.setId(1);
                }
                if (role.getName().equals("USER")) {
                    role.setId(2);
                }
            }
        }
        userDao.updateUser(id, user);
    }

    //удаляем user
    @Override
    @Transactional
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    //поиск пользователя по имени
    @Override
    public User searchForUserByName(String name) {
        return userDao.searchForUserByName(name);
    }
}
