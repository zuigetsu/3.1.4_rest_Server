package server.service;


import server.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User getById(Integer id);

    List<User> getAll();

    void deleteById(Integer id);

    boolean isExist(Integer id);

    void setRolesByRoleIds(User user, Integer[] roleIds);

    User update(User user);

    User getByName(String name);
}
