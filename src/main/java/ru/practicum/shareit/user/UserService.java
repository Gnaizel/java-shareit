package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserService {

    Collection<User> getAll();

    User save(User user);

    User update(User user);

    void delete(long id);

    User getById(long id);
}
