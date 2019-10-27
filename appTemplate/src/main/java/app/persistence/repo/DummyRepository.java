package app.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.persistence.model.DummyEntity;

@Repository
public interface DummyRepository extends JpaRepository<DummyEntity, Long>{

    public List<DummyEntity> findByName(String name);

	public List<DummyEntity> findByAge(int age);
	
	public List<DummyEntity> findByCitizenship(String citizenship);
}
