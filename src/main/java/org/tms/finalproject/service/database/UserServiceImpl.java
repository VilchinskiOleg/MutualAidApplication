package org.tms.finalproject.service.database;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.tms.finalproject.entity.Comment;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.repository.UserRepository;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null!");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void updateByFieldNameAndValue(String name, String value) {
        User actualUser = getActualUser();
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, actualUser, value);
                field.setAccessible(false);
            }
        }
        userRepository.save(actualUser);

//        try {
//            Field currentField = User.class.getDeclaredField(name);
//            currentField.setAccessible(true);
//            currentField.set(actualUser, value);
//            currentField.setAccessible(false);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteUserById(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(RuntimeException :: new);
    }

    @Override
    public User getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("UserName is null!");
        }
        Optional<User> user = userRepository.findByUserName(login);
        return user.orElseThrow(RuntimeException :: new);
    }

    @Override
    public User getActualUser() {
        String userName = getActualUserName();
        return getUserByLogin(userName);
    }

    @Override
    public String getActualUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return  ((org.springframework.security.core.userdetails.User) context.getAuthentication().getPrincipal()).getUsername();
    }

    @Override
    public void recalculateAverageRatingForUserById(long userId) {
        // Почему-то юзер приходит с не совсем актуальным списком коментов, последнего нет. Разобраться.
        User user = getUserById(userId);
        Double ratingSum = user.getComments()
                .stream()
                .map(Comment::getRating)
                .reduce(Double::sum)
                .orElse(0.0);
        user.setRating(ratingSum / user.getComments().size());
        userRepository.save(user);
    }
}
