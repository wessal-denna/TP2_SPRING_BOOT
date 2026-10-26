package ma.ensaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensaf.model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}