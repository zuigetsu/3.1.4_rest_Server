package server.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.dao.RoleDAO;
import server.dao.UserDAO;
import server.exceptions.IncorrectUserDataException;
import server.model.User;
import server.service.UserService;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        userDAO.deleteById(id);
    }

    @Override
    public boolean isExist(Integer id) {
        return userDAO.existById(id);
    }

    @Override
    public void setRolesByRoleIds(User user, Integer[] roleIds) {
        user.setRoles(new HashSet<>());
        for (int id : roleIds) {
            user.getRoles().add(roleDAO.getById(id));
        }
    }

    @Transactional
    @Override
    public User update(User user) {
        String oldPassword = user.getPassword();
        boolean isPasswordEmpty = oldPassword == null || oldPassword.equals("");
        boolean isUserExist = user.getId() != null && userDAO.existById(user.getId());

        if (!isUserExist && isPasswordEmpty) {
            throw new IncorrectUserDataException();
        }

        if (isPasswordEmpty) {
            user.setPassword(userDAO.getById(user.getId()).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(oldPassword));
        }
        return userDAO.update(user);
    }

    @Override
    public User getByName(String name) {
        return userDAO.getByName(name);
    }
}
