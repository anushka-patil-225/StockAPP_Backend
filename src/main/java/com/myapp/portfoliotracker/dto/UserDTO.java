package com.myapp.portfoliotracker.dto;

import java.util.List;

public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String password;
    private List<StockDTO> stocks;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDTO> stocks) {
        this.stocks = stocks;
    }
}
