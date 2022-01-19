package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import web.service.UserServiceSecurity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    // открытие страницы авторизации
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // обработчик выхода Spring Security
    @PostMapping("/logout")
    public String logout() {
        return "login";
    }

    // открытие страницы приветствия авторизованного пользователя
    @GetMapping("/hello")
    public String printWelcome(Model model) {
        User user = userService.searchForUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "user/user";
    }

    //получаем всех пользователь от dao и передаем их для отображения в представление
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }

    //передаем пустого пользователя на форму
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "/admin/new";
    }

    //добавляем пользователя, переданного с формы, в БД
    @PostMapping()
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/new";
        }
        userService.addUser(user.getUsername(), user.getLastName(), user.getAge(), user.getPassword(), user.getRoles());
        return "redirect:/";
    }

    //возвращает в форму пользователя по id
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.searchUser(id));
        return "/admin/edit";
    }

    //принимаем из формы нового пользователя и обновляем его по id в БД
    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/admin/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/";
    }

    //удаляем из БД пользователя по id
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }
}
