package com.library.mslibrary.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-batch")
@RibbonClient(name = "ms-batch")
public interface MicroServiceBatchProxy {

}
