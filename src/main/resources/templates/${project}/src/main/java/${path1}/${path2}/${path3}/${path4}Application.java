package ${path1}.${path2}.${path3};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @desc 启动类
 * @author ${author}
 * @date ${dateTime}
 */
@SpringBootApplication
@MapperScan(basePackages = "${path1}.${path2}.${path3}.**.mapper")
public class ${path4}Application {
    public static void main(String[] args) {
        SpringApplication.run(${path4}Application.class, args);
    }

}
