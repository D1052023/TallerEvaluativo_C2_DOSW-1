package eci.edu.dosw.taller.repositories;

import eci.edu.dosw.taller.models.Receta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends MongoRepository<Receta, String> {
}
