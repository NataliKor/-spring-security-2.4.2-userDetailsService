package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    //выводим всех users
    List<User> getAllUsers();

    //добавляем user
    void addUser(String name, String lastName, int age, String password, Set<Role> roles);

    //поиск пользователя по id
    User searchUser(int id);

    //изменяем user
    void updateUser(int id, User user);

    //удаляем user
    void deleteUserById(int id);

    //поиск пользователя по имени
    User searchForUserByName(String name);
}
