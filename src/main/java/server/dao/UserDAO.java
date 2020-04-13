package server.dao;

import server.model.User;

import java.util.List;

public interface UserDAO {

    User save(User user);

    User update(User user);

    User getById(Integer id);

    User getByName(String name);

    List<User> getAll();

    void deleteById(Integer id);

    boolean existById(Integer id);
}
