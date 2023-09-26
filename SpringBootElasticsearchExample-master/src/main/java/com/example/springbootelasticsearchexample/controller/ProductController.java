package com.example.springbootelasticsearchexample.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.eql.HitsEvent;
import com.example.springbootelasticsearchexample.SpringBootElasticsearchExampleApplication;
import com.example.springbootelasticsearchexample.entity.Product;
import com.example.springbootelasticsearchexample.service.ElasticSearchService;
import com.example.springbootelasticsearchexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apis")
public class ProductController {

    @Autowired
    private ProductService  productService;

    @Autowired
    private ElasticSearchService elasticSearchService ;
    @GetMapping("/findAll")
    Iterable<Product> findAll(){
       return productService.getProducts();

    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody  Product product){
       return productService.insertProduct(product);
    }

    @GetMapping("/matchAll")
    public SearchResponse<Map> matchAll() throws IOException {
        SearchResponse searchResponse =  elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse;

    }
    @GetMapping("/matchAllProducts")
    public List<Product> matchAllProducts() throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchAllProductServices();
        List<Hit<Product>> hitList =searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;

    }
    @GetMapping("/matchAllProductsWithName/{fieldValue}")
    public List<Product> matchAllProductsWithName(@PathVariable  String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductWithName(fieldValue);
        List<Hit<Product>> hitList =searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;

    }


}
