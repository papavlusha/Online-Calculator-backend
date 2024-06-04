package by.spaces.calculator.repository;

import by.spaces.calculator.model.Example;
import by.spaces.calculator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExampleRepository extends CrudRepository<Example, Long> {
    @Transactional
    Example findExampleByExampleId(Long id);

    @Transactional
    Example findExamplesByUser(User u);
}
