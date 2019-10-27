package app.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.web.exceptions.EntityIdMismatchException;
import app.controllers.web.exceptions.EntityNotFoundException;
import app.persistence.model.DummyEntity;
import app.persistence.repo.DummyRepository;

@RestController
@RequestMapping("/web/dummy")
public class WebController {

    @Autowired
    private DummyRepository repository;
    
    @GetMapping
    public Iterable<DummyEntity> findAll() {
        return repository.findAll();
    }

    @GetMapping("/name/{DummyEntityName}")
    public List<DummyEntity> findByTitle(@PathVariable String DummyEntityName) {
        return repository.findByName(DummyEntityName);
    }

    @GetMapping("/{id}")
    public DummyEntity findOne(@PathVariable long id) {
        return repository.findById(id)
          .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DummyEntity create(@RequestBody DummyEntity DummyEntity) {
        DummyEntity DummyEntity1 = repository.save(DummyEntity);
        return DummyEntity1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.findById(id)
          .orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public DummyEntity updateDummyEntity(@RequestBody DummyEntity DummyEntity, @PathVariable long id) {
        if (DummyEntity.getId() != id) {
            throw new EntityIdMismatchException();
        }
        repository.findById(id)
          .orElseThrow(EntityNotFoundException::new);
        return repository.save(DummyEntity);
    }
	
}
