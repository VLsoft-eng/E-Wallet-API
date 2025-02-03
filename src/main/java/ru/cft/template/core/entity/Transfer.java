package ru.cft.template.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.cft.template.core.enumeration.TransferStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "amount", nullable = false)
    Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reciever_wallet_id", nullable = false)
    Wallet receiverWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_wallet_id", nullable = false)
    Wallet senderWallet;

    @Column(name = "transfer_status", nullable = false)
    TransferStatus transferStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}
