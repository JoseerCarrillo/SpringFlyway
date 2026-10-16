package com.empresa.migracion.service;

import com.empresa.migracion.model.Category;
import com.empresa.migracion.model.Product;
import com.empresa.migracion.model.Tag;
import com.empresa.migracion.repository.CategoryRepository;
import com.empresa.migracion.repository.ProductRepository;
import com.empresa.migracion.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    @Transactional
    public Product create(String name, BigDecimal price, Integer stock, Long categoryId, Set<String> tagNames) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        if (tagNames != null) {
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                product.addTag(tag);
            }
        }

        return productRepository.save(product);
    }
}
