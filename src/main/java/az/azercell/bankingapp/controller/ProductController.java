package az.azercell.bankingapp.controller;

import az.azercell.bankingapp.model.request.AuthenticationRequest;
import az.azercell.bankingapp.model.request.UserRegisterRequest;
import az.azercell.bankingapp.model.response.AuthenticationResponse;
import az.azercell.bankingapp.repository.dao.ProductDao;
import az.azercell.bankingapp.service.AuthenticationService;
import az.azercell.bankingapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/v1")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDao>> getProducts() {
        log.debug("get all products start");
        List<ProductDao> products = productService.getProducts();
        log.debug("get all products end");
        return ResponseEntity.ok(products);
    }
}
