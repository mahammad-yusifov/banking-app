package az.azercell.bankingapp.repository;

import az.azercell.bankingapp.repository.dao.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDao, Integer> {
}
