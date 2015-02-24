package hu.fnf.devel.wishbox.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@Component
public class RestIMP implements RestAPI {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User test() {
        return userRepository.findOne( 1L );
    }
}
