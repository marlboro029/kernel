/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.zytcft.kernel.codegen;

import com.zytcft.kernel.common.datasource.annotation.EnableDynamicDataSource;
import com.zytcft.kernel.common.security.annotation.EnableKernelFeignClients;
import com.zytcft.kernel.common.security.annotation.EnableKernelResourceServer;
import com.zytcft.kernel.common.swagger.annotation.EnableKernelSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author lengleng
 * @date 2018/07/29
 * 代码生成模块
 */
@EnableDynamicDataSource
@EnableKernelSwagger2
@SpringCloudApplication
@EnableKernelFeignClients
@EnableKernelResourceServer
public class KernelCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KernelCodeGenApplication.class, args);
	}
}
