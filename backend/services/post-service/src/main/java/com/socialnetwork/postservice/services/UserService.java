package com.socialnetwork.postservice.services;

import com.socialnetwork.shared.web.services.UserCredentialsService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public interface UserService extends UserCredentialsService {
}
