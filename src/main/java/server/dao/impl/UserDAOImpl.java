package server.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import server.dao.UserDAO;
import server.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager em;

    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        em.merge(user);
        return user;
    }

    @Override
    public User getById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByName(String username) {
        TypedQuery<User> query = em.createQuery("FROM User u JOIN FETCH u.roles WHERE u.username=:username", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        users = users.stream().distinct().collect(Collectors.toList());
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("FROM User u JOIN FETCH u.roles", User.class);
        List<User> users = query.getResultList();
        users = users.stream().distinct().collect(Collectors.toList());
        return users;
    }

    @Override
    public void deleteById(Integer id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public boolean existById(Integer id) {
        return em.find(User.class, id) != null;
    }
}
