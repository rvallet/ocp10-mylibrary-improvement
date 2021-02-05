package com.library.website.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ms-batch")
@RibbonClient(name = "ms-batch")
public interface MicroServiceBatchProxy {

    @GetMapping(value = "/availableNotification/{bookId}")
    void sendBookAvailableNotification(Long bookId);

}
