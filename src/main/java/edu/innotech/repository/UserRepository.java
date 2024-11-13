package edu.innotech.repository;

import edu.innotech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        select user
        from User user
        where user.id = :userId
       """)

    public User findByUserId(@Param("userId") Long userId);
}
