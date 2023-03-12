package emlakcepte.repository;

import emlakcepte.model.Realty;
import emlakcepte.model.enums.RealtyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealtyRepository extends JpaRepository<Realty, Integer> {

	List<Realty> findAllByUserId(int id);

	List<Realty> findAllByStatus(RealtyType active);

}
