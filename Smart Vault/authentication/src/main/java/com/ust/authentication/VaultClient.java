//package com.ust.authentication;
//
//import com.ust.authentication.dto.VaultResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "user-info-service", url = "http://localhost:2020")
//public interface VaultClient {
//    @GetMapping("/vaults/{id}")
//    ResponseEntity<VaultResponse> getVault(@PathVariable Long id);
//}
