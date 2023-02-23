package id.my.bumantara.portfoliospring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import id.my.bumantara.portfoliospring.models.User;

@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {

}
