package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.cft.template.core.entity.Transfer;
import ru.cft.template.core.enumeration.TransferStatus;

import java.util.List;
import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query("SELECT t FROM Transfer t " +
            "WHERE (t.senderWallet.user.id = :userId OR t.receiverWallet.user.id = :userId) " +
            "AND t.transferStatus = :transferStatus")
    List<Transfer> findAllByParams(UUID userId, TransferStatus transferStatus);

    @Query("SELECT t FROM Transfer t " +
            "WHERE ((t.senderWallet.user.id = :userId AND t.receiverWallet.user.id = :receiverId) " +
            "   OR (t.senderWallet.user.id = :receiverId AND t.receiverWallet.user.id = :userId)) " +
            "AND t.transferStatus = :transferStatus")
    List<Transfer> findAllByParamsAndCollaborate(UUID userId, UUID receiverId, TransferStatus transferStatus);
}
