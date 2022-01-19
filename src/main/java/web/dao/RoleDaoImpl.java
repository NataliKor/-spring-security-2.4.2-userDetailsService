package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //выводим все roles
    @Override
    public List<Role> getAllRole() {
        List<Role> list;
        String sql = "SELECT role FROM Role role";
        Session session = sessionFactory.getCurrentSession();
        list = session.createQuery(sql).list();
        return list;
    }

    //поиск роли по id
    @Override
    public Role searchRole(int id) {
        Role resultRole;
        String sql = "SELECT role FROM Role role WHERE role.id = ?1";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter(1, id);
        try {
            resultRole = (Role) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return resultRole;
    }

    //поиск роли по имени
    @Override
    public Role searchForRoleByName(String name) {
        Role resultRole;
        String sql = "SELECT role FROM Role role WHERE role.name = ?1";
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(sql);
        query.setParameter(1, name);
        try {
            resultRole = (Role) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return resultRole;
    }
}
