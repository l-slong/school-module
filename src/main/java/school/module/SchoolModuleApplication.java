package school.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("school.module.dao") // 解决扫描不到dao下的EntryMapper类的问题
public class SchoolModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolModuleApplication.class, args);
    }

}
