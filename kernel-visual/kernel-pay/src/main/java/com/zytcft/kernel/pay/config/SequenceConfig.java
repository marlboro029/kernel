package com.zytcft.kernel.pay.config;

import cn.hutool.core.date.DateUtil;
import com.zytcft.kernel.common.sequence.builder.DbSeqBuilder;
import com.zytcft.kernel.common.sequence.properties.SequenceDbProperties;
import com.zytcft.kernel.common.sequence.sequence.Sequence;
import com.zytcft.kernel.common.data.tenant.TenantContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lengleng
 * @date 2019-05-26
 * <p>
 * 设置发号器生成规则
 */
@Configuration
public class SequenceConfig {

	/**
	 * 订单流水号发号器
	 *
	 * @param dataSource
	 * @param properties
	 * @return
	 */
	@Bean
	public Sequence paySequence(DataSource dataSource,
								SequenceDbProperties properties) {
		return DbSeqBuilder
				.create()
				.bizName(() -> String.format("pay_%s_%s", TenantContextHolder.getTenantId(), DateUtil.today()))
				.dataSource(dataSource)
				.step(properties.getStep())
				.retryTimes(properties.getRetryTimes())
				.tableName(properties.getTableName())
				.build();
	}

	/**
	 * 通道编号发号器
	 *
	 * @param dataSource
	 * @param properties
	 * @return
	 */
	@Bean
	public Sequence channelSequence(DataSource dataSource,
									SequenceDbProperties properties) {
		return DbSeqBuilder
				.create()
				.bizName(() -> String.format("channel_%s", TenantContextHolder.getTenantId()))
				.dataSource(dataSource)
				.step(properties.getStep())
				.retryTimes(properties.getRetryTimes())
				.tableName(properties.getTableName())
				.build();
	}
}
