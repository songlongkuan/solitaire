package cn.pencilso.solitaire.start;

import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StartApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<UserModel> list = userService.list();
        System.out.println(list);

    }

}
