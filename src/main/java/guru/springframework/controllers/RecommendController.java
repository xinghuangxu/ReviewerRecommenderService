package guru.springframework.controllers;

import guru.springframework.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@Controller
public class RecommendController {

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public String getRecommend(HttpServletRequest request){
        return "Hello World!";
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public String saveRecommend(HttpServletRequest request){
        return "Hello World!";
    }
}
