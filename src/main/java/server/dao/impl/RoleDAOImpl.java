package server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import server.dao.RoleDAO;
import server.model.Role;

import javax.persistence.EntityManager;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager em;

    @Autowired
    public RoleDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Role getById(Integer id) {
        return em.find(Role.class, id);
    }

}
