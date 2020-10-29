package com.symbo.insurance.productservice.bootstrapdata;

import com.symbo.insurance.productservice.domain.Products;
import com.symbo.insurance.productservice.repository.ProductRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
public class LoadProducts implements ApplicationListener<ContextRefreshedEvent> {

    ProductRepository productRepository;

    LoadProducts(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Products product1 = new Products();
        product1.setProductId("1");
        product1.setProductName("iPhone 12");
        product1.setProductDesc("Latest iPhone");
        product1.setProductRate(90000);
        product1.setStockLimit(100);

        Products product2 = new Products();
        product2.setProductId("2");
        product2.setProductName("Samsung Galaxy S5");
        product2.setProductDesc("Latest Samsung mobile phone");
        product2.setProductRate(80000);
        product2.setStockLimit(50);

        Products product3 = new Products();
        product3.setProductId("3");
        product3.setProductName("Redmi Note");
        product3.setProductDesc("Latest RedMI mobile phone");
        product3.setProductRate(20000);
        product3.setStockLimit(30);

        log.info("Saving Products");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        log.info("Products saved");
    }
}
