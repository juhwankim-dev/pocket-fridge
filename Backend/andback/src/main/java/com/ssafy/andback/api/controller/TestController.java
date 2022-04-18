package com.ssafy.andback.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
*
* TestController
* Controller Test 파일
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-18
* 마지막 수정일 2022-04-18
**/
public class TestController {

    @RequestMapping(value = "/HelloWorld", method = RequestMethod.GET)
    public String HelloWorld(){
        return "Hello World";
    }

}
