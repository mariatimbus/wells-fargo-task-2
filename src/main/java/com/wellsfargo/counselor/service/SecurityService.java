package com.wellsfargo.counselor.service;

import com.wellsfargo.counselor.entity.Portfolio;
import com.wellsfargo.counselor.entity.Security;
import com.wellsfargo.counselor.repository.PortfolioRepository;
import com.wellsfargo.counselor.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SecurityService {

    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, PortfolioRepository portfolioRepository) {
        this.securityRepository = securityRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public Iterable<Security> getSecuritiesByPortfolioId(long portfolioId) {
        return securityRepository.findByPortfolioPortfolioId(portfolioId);
    }

    public Optional<Security> getSecurityById(long securityId) {
        return securityRepository.findById(securityId);
    }

    public Security createSecurity(long portfolioId, String name, String category, LocalDate purchaseDate, double purchasePrice, int quantity) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio not found: " + portfolioId);
        }

        Security security = new Security(portfolio.get(), name, category, purchaseDate, purchasePrice, quantity);
        return securityRepository.save(security);
    }

    public Optional<Security> updateSecurity(long securityId, String name, String category, LocalDate purchaseDate, double purchasePrice, int quantity) {
        Optional<Security> existing = securityRepository.findById(securityId);
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        Security security = existing.get();
        security.setName(name);
        security.setCategory(category);
        security.setPurchaseDate(purchaseDate);
        security.setPurchasePrice(purchasePrice);
        security.setQuantity(quantity);

        return Optional.of(securityRepository.save(security));
    }

    public boolean deleteSecurity(long securityId) {
        if (!securityRepository.existsById(securityId)) {
            return false;
        }
        securityRepository.deleteById(securityId);
        return true;
    }
}
