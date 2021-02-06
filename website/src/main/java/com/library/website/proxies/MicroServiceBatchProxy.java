package com.library.website.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-batch", url = "localhost:9095")
@RibbonClient(name = "ms-batch")
public interface MicroServiceBatchProxy {

    @GetMapping(value = "/availableBookNotification/{bookId}")
    void sendBookAvailableNotification(@PathVariable("bookId") Long bookId);

}
