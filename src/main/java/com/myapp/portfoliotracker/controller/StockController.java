package com.myapp.portfoliotracker.controller;

import com.myapp.portfoliotracker.entity.Stock;
import com.myapp.portfoliotracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.addStock(stock);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    @PutMapping("/{stockId}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long stockId, @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(stockId, stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Stock>> getStocksByUserId(@PathVariable Long userId) {
        List<Stock> stocks = stockService.getStocksByUserId(userId);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/portfolio-value/{userId}")
    public ResponseEntity<Double> getPortfolioValue(@PathVariable Long userId) {
        double portfolioValue = stockService.getPortfolioValue(userId);
        return ResponseEntity.ok(portfolioValue);
    }
}
