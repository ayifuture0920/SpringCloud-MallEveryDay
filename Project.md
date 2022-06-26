### 1. 创建项目微服务

- 商品服务
- 仓储服务
- 订单服务
- 优惠券服务
- 用户服务  

每个服务模块的共同点：

- 每个模块都需要引入 spring-web 和 openfeign 的依赖
- 每一个服务，包名都是 com.mall.xxx(goods / stock / order / coupon / user)
-  模块名： mall-goods / mall-stock / mall-order / mall-coupon / mall-user