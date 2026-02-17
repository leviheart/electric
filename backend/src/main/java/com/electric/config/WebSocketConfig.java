package com.electric.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig - WebSocket 配置类
 * 
 * 功能说明：
 * 配置 WebSocket 消息代理，用于实时推送告警信息
 * 使用 STOMP 协议作为 WebSocket 的子协议
 * 
 * WebSocket 通信流程：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 客户端                           服务器                          │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 1. 连接 WebSocket                                                │
 * │    ws://localhost:8080/ws  ──────────────────►  建立连接         │
 * │                                                                 │
 * │ 2. 订阅告警主题                                                   │
 * │    SUBSCRIBE /topic/alerts  ─────────────────►  注册订阅         │
 * │                                                                 │
 * │ 3. 接收告警推送                                                   │
 * │    ◄──────────────────  MESSAGE /topic/alerts                   │
 * │                                                                 │
 * │ 4. 发送告警确认                                                   │
 * │    SEND /app/alerts/ack  ──────────────────►  处理确认           │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * STOMP 协议说明：
 * - STOMP = Simple Text Oriented Messaging Protocol
 * - 提供了订阅/发布模式，比原生 WebSocket 更易用
 * - 支持消息头、消息体等结构化数据
 * 
 * 端点说明：
 * - /ws: WebSocket 连接端点
 * - /topic/*: 广播主题（一对多）
 * - /queue/*: 点对点队列（一对一）
 * - /app/*: 客户端发送消息的前缀
 * 
 * 文件关联：
 * - AlertService: 使用 SimpMessagingTemplate 推送告警
 * - AlertController: 处理客户端发送的消息
 * - 前端: 使用 STOMP 客户端连接
 * 
 * 新人提示：
 * - @EnableWebSocketMessageBroker 启用 WebSocket 消息代理
 * - configureMessageBroker 配置消息代理
 * - registerStompEndpoints 注册 WebSocket 端点
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     * 
     * 消息代理负责：
     * 1. 管理客户端订阅
     * 2. 路由消息到正确的订阅者
     * 3. 处理消息的发送和接收
     * 
     * @param config 消息代理注册器
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用简单的内存消息代理
        // /topic: 广播主题，所有订阅者都能收到消息
        // /queue: 点对点队列，只有特定用户能收到消息
        config.enableSimpleBroker("/topic", "/queue");
        
        // 设置客户端发送消息的前缀
        // 客户端发送到 /app/xxx 的消息会被路由到 @MessageMapping 方法
        config.setApplicationDestinationPrefixes("/app");
        
        // 设置用户目的地前缀（用于点对点消息）
        config.setUserDestinationPrefix("/user");
    }

    /**
     * 注册 STOMP 端点
     * 
     * STOMP 端点是客户端连接 WebSocket 的入口
     * 
     * @param registry STOMP 端点注册器
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 WebSocket 端点
        // 客户端通过 ws://localhost:8080/ws 连接
        registry.addEndpoint("/ws")
                // 允许跨域（开发环境使用 *，生产环境应限制域名）
                .setAllowedOriginPatterns("*")
                // 启用 SockJS 降级方案
                // 如果浏览器不支持 WebSocket，会自动降级到其他传输方式
                .withSockJS();
    }
}
