package com.zytcft.kernel.daemon.quartz;

import com.zytcft.kernel.common.security.annotation.EnableKernelFeignClients;
import com.zytcft.kernel.common.security.annotation.EnableKernelResourceServer;
import com.zytcft.kernel.common.swagger.annotation.EnableKernelSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author frwcloud
 * @date 2019/01/23
 * 定时任务模块
 */
@EnableKernelSwagger2
@SpringCloudApplication
@EnableKernelFeignClients
@EnableKernelResourceServer
public class KernelDaemonQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(KernelDaemonQuartzApplication.class, args);
	}
}
