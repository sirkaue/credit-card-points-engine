package com.sirkaue.creditcardpointsengine.infrastructure.controllers;

import com.sirkaue.creditcardpointsengine.application.usecase.CalculatePointsUseCase;
import com.sirkaue.creditcardpointsengine.application.usecase.PointsCalculationResult;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.request.TransactionRequest;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.response.PointsResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
public class PointsController {

    private final CalculatePointsUseCase calculatePointsUseCase;
    private final TransactionMapper transactionMapper;
    private final PointsResponseMapper responseMapper;

    public PointsController(CalculatePointsUseCase calculatePointsUseCase,
                            TransactionMapper transactionMapper,
                            PointsResponseMapper responseMapper) {
        this.calculatePointsUseCase = calculatePointsUseCase;
        this.transactionMapper = transactionMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public PointsResponse calculate(@RequestBody TransactionRequest request) {
        Transaction tx = transactionMapper.toTransaction(request);
        PointsCalculationResult result = calculatePointsUseCase.execute(tx);
        return responseMapper.toResponse(tx, result);
    }
}
