package com.myapp.portfoliotracker.repository;

import com.myapp.portfoliotracker.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    List<Stock> findByUserId(Long userId);
}
