package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //выводим всех users
    @Override
    public List<User> getAllUsers() {
        List<User> list;
        String sql = "SELECT user FROM User user";
        Session session = sessionFactory.getCurrentSession();
        list = session.createQuery(sql).list();
        return list;
    }

    //добавляем user
    @Override
    public void addUser(String name, String lastName, int age, String password, Set<Role> roles) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User(name, lastName, age, password, roles);
        session.save(user);
    }

    //ищем пользователя по id
    @Override
    public User searchUser(int id) {
        User resultFind;
        String sql = "SELECT user FROM User user WHERE user.id = ?1";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter(1, id);
        try {
            resultFind = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return resultFind;
    }

    //изменяем user
    @Override
    @Transactional
    public void updateUser(int id, User user) {
        sessionFactory.openSession().saveOrUpdate(user);
//        String sql = "UPDATE User SET username = ?1, lastName = ?2, age = ?3, password = ?4 WHERE id = ?5";
//        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery(sql);
//        query.setParameter(1, user.getUsername());
//        query.setParameter(2, user.getLastName());
//        query.setParameter(3, user.getAge());
//        query.setParameter(4, user.getPassword());
//        query.setParameter(5, id);
//        query.executeUpdate();
//        session.flush();
    }

    //удаляем user
    @Override
    public void deleteUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(User.class, id));
    }

    //поиск пользователя по имени
    @Override
    public User searchForUserByName(String name) {
        User resultUser = null;
        String sql = "SELECT user FROM User user WHERE user.username = ?1";
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(sql);
        query.setParameter(1, name);
        try {
            resultUser = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return resultUser;
    }
}
