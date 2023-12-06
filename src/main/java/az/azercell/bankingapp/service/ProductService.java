package az.azercell.bankingapp.service;

import az.azercell.bankingapp.repository.ProductRepository;
import az.azercell.bankingapp.repository.dao.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDao> getProducts() {
        return productRepository.findAll();
    }
}
