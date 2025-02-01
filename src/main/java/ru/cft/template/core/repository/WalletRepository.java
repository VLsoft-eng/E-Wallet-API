package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByUserId(UUID userId);
}
