//package com.shooter.springboot.module;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.ywwl.customer.module.domain.Merchant;
//import com.ywwl.customer.module.service.MerchantService;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.util.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@Slf4j
//@SpringBootTest
//class MerchantServiceTests {
//
//    @Autowired
//    private MerchantService merchantService;
//
//    /**
//     * 根据商户号查询
//     * LambdaQueryWrapper:  get 查询单行 remove 删除 list 查询集合 page 分页
//     * */
//    @Test
//    void selectByMerchantCode() {
//        // 定义查询条件
//        String merchantCode = "20013115";
//        val queryWrapper = new LambdaQueryWrapper<Merchant>();
//        // select * from tc_merchant where merchant_code = '20013115'
//        queryWrapper.eq( Merchant::getMerchantCode,merchantCode);
//        val merchant = merchantService.getOne(queryWrapper);
//        log.info("查询商户名称：{}",merchant.getMerchantName());
//    }
//
//    /**
//     * 根据商户名批量查询
//     * LambdaQueryWrapper:  get 查询单行 remove 删除 list 查询集合 page 分页
//     * */
//    @Test
//    void selectByMerchantName() {
//        // 定义查询条件　
//        String merchantName = "测试";
//        // select * from tc_merchant where merchant_name = '测试'
//        val queryWrapper = new LambdaQueryWrapper<Merchant>();
//        queryWrapper.eq(Merchant::getMerchantName,merchantName);
//        val merchants = merchantService.list(queryWrapper);
//        merchants.forEach(System.out::println);
//    }
//
//    /**
//     * 根据商户名批量查询
//     * LambdaUpdateWrapper
//     * */
//    @Test
//    void updateMerchantName() {
//        // 定义查询条件　
//        String merchantCode = "20013115";
//
//        // 更新管理员手机号
//        // UPDATE tc_merchant SET admin_phone=12345678
//        // WHERE delete_flag=0 AND (merchant_code = '20013115' AND admin_phone LIKE '%1%' AND (apply_type < 5 OR merchant_code IS NOT NULL));
//        val updateWrapper = new LambdaUpdateWrapper<Merchant>();
//        updateWrapper
//                .set(Merchant::getAdminPhone, 12345678)
//                .eq(StringUtils.isNotBlank(merchantCode), Merchant::getMerchantCode,merchantCode)
//                .like(Merchant::getAdminPhone, 1)
//                .and(i -> i.lt(Merchant::getApplyType, 5).or().isNotNull(Merchant::getMerchantCode)); //lambd
//        merchantService.update(updateWrapper);
//
//        // 再次查询商户信息
//        val queryWrapper = new LambdaQueryWrapper<Merchant>();
//        queryWrapper
//                .eq(StringUtils.isNotBlank(merchantCode), Merchant::getMerchantCode,merchantCode);
//        val merchant = merchantService.getOne(queryWrapper);
//
//        log.info("查询商户更新后手机号：{}",merchant.getAdminPhone());
//    }
//
//    /**
//     * @TableLogic注解测试
//     * */
//    @Test
//    void testTableLogic() {
//        // 定义查询条件　
//        String merchantCode = "20013115";
//        val queryWrapper = new LambdaQueryWrapper<Merchant>();
//        queryWrapper.eq(Merchant::getMerchantCode,merchantCode);
//
//        // 查询商户信息
//        // select * from tc_merchant where delete_flag=0 AND merchant_code = '20013115'
//        val oldMerchant = merchantService.getOne(queryWrapper);
//        // 查询商户删除前的更新标识：0
//        log.info("查询商户删除前的更新标识：{}",oldMerchant.getDeleteFlag());
//
//        // 删除商户信息
//        // 将delete_flag更新为1，而非删除记录
//        merchantService.remove(queryWrapper);
//
//        // 再次查询商户信息
//        // select * from tc_merchant where delete_flag=0 AND merchant_code = '20013115'
//        val newMerchant = merchantService.getOne(queryWrapper);
//        //打印true
//        log.info("查询商户删除后的更新标识：{}",newMerchant == null);
//    }
//
//}
