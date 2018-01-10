<#import "global.ftlh" as global/>
package com.hexin.<@global.obj/>.controller;

import com.hexin.<@global.obj/>.domain.<@global.class/>;
import com.hexin.<@global.obj/>.service.<@global.class/>Service;
import com.hexin.common.controller.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
<@global.fileComment/>
@Controller
@RequestMapping("/<@global.obj/>")
public class <@global.class/>Controller extends CommonController {
    private static final Logger logger = LoggerFactory.getLogger(<@global.class/>Controller.class);
    @Autowired private <@global.class/>Service <@global.obj/>Service;

    @RequestMapping("/toAdd")
    public ModelAndView toAdd<@global.class/>(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jsp/<@global.obj/>/add<@global.class/>");
        return mv;
    }
    @RequestMapping("/add")
    @ResponseBody
    public int add<@global.class/>(@ModelAttribute <@global.class/> <@global.obj/>){
        return <@global.obj/>Service.add(<@global.obj/>);
    }
    @RequestMapping("/toEdit/{id}")
    public ModelAndView toEdit<@global.class/>(@PathVariable("id") int cId){
        <@global.class/> <@global.obj/> = <@global.obj/>Service.queryOneById(cId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("<@global.obj/>", <@global.obj/>);
        mv.setViewName("jsp/<@global.obj/>/add<@global.class/>");
        return mv;
    }
    @RequestMapping("/edit")
    @ResponseBody
    public int edit<@global.class/>(@ModelAttribute <@global.class/> <@global.obj/>){
        int result = <@global.obj/>Service.modify(<@global.obj/>);
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public int del<@global.class/>(@RequestParam("id") int id){
        int result = <@global.obj/>Service.remove(id);
        return result;
    }

}