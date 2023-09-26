package com.example.springbootelasticsearchexample.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.springbootelasticsearchexample.entity.Product;
import com.example.springbootelasticsearchexample.utils.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient ;

    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();

        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
        System.out.println(supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> matchAllProductServices() throws IOException {

        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Product> searchResponse = elasticsearchClient.
                search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println(searchResponse.toString());

        return searchResponse;
    }
    public SearchResponse<Product> matchProductWithName(String fieldValue) throws IOException {

        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.
                search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println(searchResponse.toString());

        return searchResponse;
    }
}
