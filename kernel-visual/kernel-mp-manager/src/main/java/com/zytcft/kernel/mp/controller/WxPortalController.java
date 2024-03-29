package com.zytcft.kernel.mp.controller;

import cn.hutool.core.util.StrUtil;
import com.zytcft.kernel.common.core.constant.SecurityConstants;
import com.zytcft.kernel.common.security.annotation.Inner;
import com.zytcft.kernel.mp.config.WxMpConfiguration;
import com.zytcft.kernel.mp.config.WxMpContextHolder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Binary Wang
 * @author lengleng
 * <p>
 * 微信接入入口
 */
@Slf4j
@Inner(value = false)
@RestController
@RequestMapping("/{appId}/portal")
public class WxPortalController {

	/**
	 * 微信接入校验处理
	 *
	 * @param appId     多公众号标志位
	 * @param signature 微信签名
	 * @param timestamp 时间戳
	 * @param nonce     随机数
	 * @param echostr   随机字符串
	 * @return
	 */
	@GetMapping(produces = "text/plain;charset=utf-8")
	public String authGet(@PathVariable("appId") String appId,
						  @RequestParam(name = "signature", required = false) String signature,
						  @RequestParam(name = "timestamp", required = false) String timestamp,
						  @RequestParam(name = "nonce", required = false) String nonce,
						  @RequestParam(name = "echostr", required = false) String echostr) {

		log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实!");
		}

		final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);

		if (wxService == null) {
			throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appId));
		}

		if (wxService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}

		return "非法请求";
	}

	/**
	 * 微信消息处理
	 *
	 * @param appId        多公众号标志位
	 * @param requestBody  请求报文体
	 * @param signature    微信签名
	 * @param encType      加签方式
	 * @param msgSignature 微信签名
	 * @param timestamp    时间戳
	 * @param nonce        随机数
	 * @return
	 */
	@PostMapping(produces = "application/xml; charset=UTF-8")
	public String post(@PathVariable("appId") String appId,
					   @RequestBody String requestBody,
					   @RequestParam("signature") String signature,
					   @RequestParam("timestamp") String timestamp,
					   @RequestParam("nonce") String nonce,
					   @RequestParam("openid") String openid,
					   @RequestParam(name = "encrypt_type", required = false) String encType,
					   @RequestParam(name = "msg_signature", required = false) String msgSignature) {

		WxMpContextHolder.setAppId(appId);
		final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);

		log.info("接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[{}] ",
				openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

		if (!wxService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		String out = null;

		// 明文模式
		if (StrUtil.isBlank(encType)) {
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			WxMpXmlOutMessage outMessage = WxMpConfiguration.getRouters().get(appId).route(inMessage);
			if (outMessage != null) {
				out = outMessage.toXml();
			}
		}

		// aes加密模式
		if (SecurityConstants.AES.equalsIgnoreCase(encType)) {
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
					timestamp, nonce, msgSignature);

			log.debug("消息解密后内容为：{} ", inMessage.toString());

			WxMpXmlOutMessage outMessage = WxMpConfiguration.getRouters().get(appId).route(inMessage);
			if (outMessage != null) {
				out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
			}
		}

		log.debug("组装回复信息：{}", out);
		WxMpContextHolder.clear();
		return out;
	}

}
