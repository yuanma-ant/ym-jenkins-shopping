package bat.ke.qq.com.controller;

import bat.ke.qq.com.common.ApiResult;
import bat.ke.qq.com.common.Product;
import bat.ke.qq.com.common.Table;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    @PostMapping("/doLogin")
    @ResponseBody
    public ApiResult doLogin(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (!Table.user.containsKey(name)) {
            return ApiResult.fail("账户名不存在");
        }

        if (!Table.user.get(name).equals(password)) {
            return ApiResult.fail("密码不正确");
        }

        if (RandomUtil.randomInt(2)==0){
            return ApiResult.fail("随机失败");
        }

        request.getSession().setAttribute("name", name);
        System.out.println("登录成功");
        return ApiResult.success("登录成功");
    }

    @GetMapping("/productList")
    @ResponseBody
    public ApiResult productList(HttpServletRequest request) {
        List<Product> productList=new ArrayList<>();
        Product p1=new Product("123456","源码学院提供洗发水");
        Product p2=new Product("989999","零食大礼包");
        productList.add(p1);
        productList.add(p2);
        return ApiResult.success(productList);
    }

    @PostMapping("/submitOrder")
    @ResponseBody
    public ApiResult submitOrder(HttpServletRequest request) {
        String name= (String) request.getSession().getAttribute("name");
        if (name==null || name.length()==0){
            return ApiResult.fail("请先登录");
        }
        String productId = request.getParameter("skuId");
        if (productId==null || productId.length()==0){
            return ApiResult.fail("请选择需要提交的商品");
        }
        return ApiResult.success("订单提交成功,商品信息:"+productId);
    }
}
