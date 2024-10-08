import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TerminateOrderService {

    @Autowired
    private TerminateOrderRepository terminateOrderRepository;

    @Autowired
    private RetryTransactionRepository retryTransactionRepository;

    public TerminateOrderDTO getOrderTransactions(String orderId, String kerberos) {
        Optional<TerminateOrder> terminateOrderOpt = terminateOrderRepository.findByOrderIdAndSubmittedBy(orderId, kerberos);

        if (!terminateOrderOpt.isPresent()) {
            throw new TerminateOrderIdNotFoundException("Terminate order ID not found: " + orderId);
        }

        TerminateOrder terminateOrder = terminateOrderOpt.get();
        List<RetryTransaction> originalTransactionDetails = retryTransactionRepository.findByTransactionIdIn(terminateOrder.getOriginalTransactionIds());

        List<RetryTransactionDTO> transactionDTOs = originalTransactionDetails.stream()
                .map(this::mapToRetryTransactionDTO)
                .collect(Collectors.toList());

        return mapToTerminateOrderDTO(terminateOrder, transactionDTOs);
    }

    // Map RetryTransaction to RetryTransactionDTO using Builder
    private RetryTransactionDTO mapToRetryTransactionDTO(RetryTransaction transaction) {
        return RetryTransactionDTO.builder()
                .id(transaction.getId())
                .fileName(transaction.getFileName())
                .feed(transaction.getFeed())
                .transactionId(transaction.getTransactionId())
                .gpsRequest(transaction.getGpsRequest())
                .gpsResponse(transaction.getGpsResponse())
                .responseText(transaction.getResponseText())
                .build();
    }

    // Map TerminateOrder to TerminateOrderDTO using Builder
    private TerminateOrderDTO mapToTerminateOrderDTO(TerminateOrder terminateOrder, List<RetryTransactionDTO> transactionDTOs) {
        return TerminateOrderDTO.builder()
                .orderId(terminateOrder.getOrderId())
                .feedType(terminateOrder.getFeedType())
                .status(terminateOrder.getStatus())
                .createdAt(terminateOrder.getCreatedAt())
                .updatedAt(terminateOrder.getUpdatedAt())
                .submittedBy(terminateOrder.getSubmittedBy())
                .submitterComments(terminateOrder.getSubmitterComments())
                .reviewedBy(terminateOrder.getReviewedBy())
                .reviewerComments(terminateOrder.getReviewerComments())
                .originalTransactionIds(terminateOrder.getOriginalTransactionIds())
                .transactionInfoDetails(transactionDTOs)
                .build();
    }
}
