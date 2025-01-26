package com.myapp.portfoliotracker.service;

import com.myapp.portfoliotracker.entity.Stock;
import com.myapp.portfoliotracker.exceptions.ResourceNotFoundException;
import com.myapp.portfoliotracker.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepo stockRepository;

    private static final String STOCK_API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=YOUR_API_KEY";

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Stock addStock(Stock stock) {
        if (stock.getQuantity() <= 0 || stock.getBuyPrice() <= 0) {
            throw new IllegalArgumentException("Quantity and Buy Price must be greater than zero.");
        }
        return stockRepository.save(stock);
    }

    @Transactional
    public Stock updateStock(Long stockId, Stock updatedStock) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with ID " + stockId + " not found."));

        existingStock.setTicker(updatedStock.getTicker());
        existingStock.setName(updatedStock.getName());
        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setBuyPrice(updatedStock.getBuyPrice());
        existingStock.setPurchaseDate(updatedStock.getPurchaseDate());
        return stockRepository.save(existingStock);
    }

    @Transactional
    public void deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Stock with ID " + stockId + " not found.");
        }
        stockRepository.deleteById(stockId);
    }

    @Transactional(readOnly = true)
    public double getPortfolioValue(Long userId) {
        List<Stock> stocks = getStocksByUserId(userId);
        return stocks.stream()
                .mapToDouble(stock -> {
                    double currentPrice = fetchStockPrice(stock.getTicker());
                    return currentPrice * stock.getQuantity();
                })
                .sum();
    }

    @Transactional(readOnly = true)
    public List<Stock> getStocksByUserId(Long userId) {
        List<Stock> stocks = stockRepository.findByUserId(userId);
        if (stocks.isEmpty()) {
            throw new ResourceNotFoundException("No stocks found for user ID " + userId + ".");
        }
        return stocks;
    }

    private double fetchStockPrice(String ticker) {
        try {
            String url = String.format(STOCK_API_URL, ticker);
            var response = restTemplate.getForObject(url, String.class);
            return extractPriceFromResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock price for ticker: " + ticker, e);
        }
    }

    private double extractPriceFromResponse(String response) {
        try {
            String priceString = response.split("\"05. price\": \"")[1].split("\"")[0];
            return Double.parseDouble(priceString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock price from response: " + response, e);
        }
    }
}
