package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User save(User user) {
        if (!user.getEmail().contains("@")) {
            throw new RuntimeException("НЕ вылидный email");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) {
                throw new RuntimeException("НЕ вылидный email");
            }
        }
        return userRepository.update(user);
    }

    @Override
    public void delete(long id) {
        boolean userIsDeleteOrNo = userRepository.delete(id);
        if (!userIsDeleteOrNo) throw new RuntimeException("in correct user id: " + id);
    }

    @Override
    public User getById(long id) {
        return userRepository.getById(id);
    }
}
