package com.mall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class MallThirdPartyApplicationTests {

    @Resource
    private OSSClient ossClient;
    /**
     * 1. 引入oss-starter
     * 2. 配置key，endpoint相关信息
     * 3. 使用OSSClient
     * @throws FileNotFoundException
     */
    @Test
    void contextLoads() throws FileNotFoundException {
        String bucketName = "mall-everyday";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "xiaojiejie.jpg";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath = "D:\\Pictures\\QQ图片20211216195533.jpg";

        FileInputStream file = new FileInputStream(filePath);
        ossClient.putObject(bucketName, objectName, file);
    }

}
