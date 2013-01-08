package springbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * springbook.controller.HelloWorldController
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 24.
 */
@Controller
public class HelloController {

    @RequestMapping(value = {"/hello.do", "/view/hello"})
    public ModelAndView hello(String name) {
        String message = String.format("안녕하세요? %s! Spring 3.1<br/><br/>멀티 라인을 제대로 표현해야지요^^", name);
        return new ModelAndView("hello", "message", message);
    }

    @RequestMapping("/complex")
    public String complex(@RequestParam Map<String, String> params, ModelMap model) {
        model.put("info", params);
        model.put("message", "abc");
        return "complex";
    }
}
