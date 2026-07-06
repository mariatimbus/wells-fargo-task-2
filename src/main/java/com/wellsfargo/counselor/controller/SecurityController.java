package com.wellsfargo.counselor.controller;

import com.wellsfargo.counselor.entity.Security;
import com.wellsfargo.counselor.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/portfolios/{portfolioId}/securities")
    public Iterable<Security> getSecuritiesByPortfolioId(@PathVariable long portfolioId) {
        return securityService.getSecuritiesByPortfolioId(portfolioId);
    }

    @GetMapping("/securities/{securityId}")
    public ResponseEntity<Security> getSecurityById(@PathVariable long securityId) {
        Optional<Security> security = securityService.getSecurityById(securityId);
        return security.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/securities")
    public ResponseEntity<Security> createSecurity(@RequestBody CreateSecurityRequest request) {
        Security security = securityService.createSecurity(
                request.portfolioId(),
                request.name(),
                request.category(),
                request.purchaseDate(),
                request.purchasePrice(),
                request.quantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(security);
    }

    @PutMapping("/securities/{securityId}")
    public ResponseEntity<Security> updateSecurity(@PathVariable long securityId, @RequestBody UpdateSecurityRequest request) {
        Optional<Security> security = securityService.updateSecurity(
                securityId,
                request.name(),
                request.category(),
                request.purchaseDate(),
                request.purchasePrice(),
                request.quantity()
        );
        return security.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/securities/{securityId}")
    public ResponseEntity<Void> deleteSecurity(@PathVariable long securityId) {
        boolean deleted = securityService.deleteSecurity(securityId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    public record CreateSecurityRequest(
            long portfolioId,
            String name,
            String category,
            LocalDate purchaseDate,
            double purchasePrice,
            int quantity
    ) {
    }

    public record UpdateSecurityRequest(
            String name,
            String category,
            LocalDate purchaseDate,
            double purchasePrice,
            int quantity
    ) {
    }
}
