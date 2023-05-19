package com.example.springsecurity.services;

import com.example.springsecurity.models.Category;
import com.example.springsecurity.models.Product;
import com.example.springsecurity.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    Список всех товаров
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

//    товар по id
    public Product getProductId(int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

//    cохранить товар
    @Transactional
    public void saveProduct(Product product, Category category){
        product.setCategory(category);
        productRepository.save(product);
    }

//    обновить информацию о продукте
@Transactional
    public void updateProduct(int id, Product product){
        product.setId(id);
        productRepository.save(product);
    }

//    удалить товар по id
@Transactional
public void deleteProduct(int id){
    productRepository.deleteById(id);
}


}
