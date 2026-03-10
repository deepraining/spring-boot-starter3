package dr.sbs.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"dr.sbs.mp.mapper", "dr.sbs.admin.dao"})
public class MyBatisConfig {}
