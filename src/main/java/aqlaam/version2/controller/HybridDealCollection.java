package aqlaam.version2.controller;

import aqlaam.version2.dto.request.HybridDealRequest;
import aqlaam.version2.dto.response.HybridDealResponse;
import aqlaam.version2.service.interfaces.IHybridDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/hybrid-deal")
@RequiredArgsConstructor
public class HybridDealCollection {

    private final IHybridDealService hybridDealService;
    private final Logger logger = LoggerFactory.getLogger(HybridDealCollection.class);


    @PostMapping("/add")
    public ResponseEntity<HybridDealResponse> createHybridDeal(@RequestBody @Valid HybridDealRequest hybridDealRequest){
        logger.info("Creating a new book deal this data (from addBookDeal methode) : {}", hybridDealRequest);
        HybridDealResponse hybridDealRequest1 = hybridDealService.add(hybridDealRequest);
        return new ResponseEntity<>(hybridDealRequest1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HybridDealResponse>> retrieveAllHybridDeals(){
        logger.info("Fetching all book deals");
        List<HybridDealResponse> hybridDealRequest1 = hybridDealService.getAllHybridDeals();
        return new ResponseEntity<>(hybridDealRequest1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HybridDealResponse> retrieveHybridDeal(@PathVariable Long id) {
        logger.info("Fetching Hybrid Deal with id: {}", id);
        HybridDealResponse hybridDeal = hybridDealService.getHybridDealById(id);
        return new ResponseEntity<>(hybridDeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHybridDeal(@PathVariable Long id) {
        logger.info("Delete Hybrid Deal with id: {}", id);
        hybridDealService.deleteDealById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<HybridDealResponse> acceptDeal(@PathVariable Long id) {
        logger.info("Fetching all accepted Deals");
        HybridDealResponse hybridDeal = hybridDealService.acceptDeal(id);
        return new ResponseEntity<>(hybridDeal, HttpStatus.OK);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<HybridDealResponse> rejectDeal(@PathVariable Long id) {
        logger.info("Fetching all rejected Deals");
        HybridDealResponse hybridDealResponse = hybridDealService.rejectDeal(id);
        return new ResponseEntity<>(hybridDealResponse, HttpStatus.OK);
    }

    @GetMapping("/accepted/{userId}")
    public ResponseEntity<List<HybridDealResponse>> getAllAcceptedDeals(@PathVariable Long userId) {
        logger.info("Fetching all accepted Deals for user: {}", userId);
        List<HybridDealResponse> hybridDeals = hybridDealService.getAllAcceptedDeals(userId);
        return new ResponseEntity<>(hybridDeals, HttpStatus.OK);
    }

    @GetMapping("/rejected/{userId}")
    public ResponseEntity<List<HybridDealResponse>> getAllRejectedDeals(@PathVariable Long userId) {
        logger.info("Fetching all rejected Deals for user: {}", userId);
        List<HybridDealResponse> hybridDeals = hybridDealService.getAllRejectedDeals(userId);
        return new ResponseEntity<>(hybridDeals, HttpStatus.OK);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<HybridDealResponse>> getAllPendingDeals(@PathVariable Long userId) {
        logger.info("Fetching all pending Deals for user: {}", userId);
        List<HybridDealResponse> hybridDeals = hybridDealService.getAllPendedDeals(userId);
        return new ResponseEntity<>(hybridDeals, HttpStatus.OK);
    }

}
