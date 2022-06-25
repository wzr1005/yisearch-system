package com.wzr.yi.controller;

import com.wzr.yi.service.KankanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:43 上午
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/kankan")
public class kankanController {
    private final KankanService kankanService;
    //看单列表中增加某个看单item
    @PostMapping("/addKankanItem")
    public boolean addKankanItem(String userId, String itemId){
        return kankanService.addkankanItem(userId, itemId);
    }
    //看单列表中移除某个看单item
    @PostMapping("/delKankanItem")
    public void delKankanItem(String userId, String itemId){
        kankanService.delkankanItem(userId, itemId);
    }

    /**
     * 新增一个看单列表
     * @param name
     */
    @PostMapping("/addKandanList")
    public void addKandanList(String name){

    }
    // 获取某用户看单item的列表
    @GetMapping("/loadKankanList")
    public List<Map<String, Object>> loadKankanList(String userId){
        return kankanService.loadkandanUserList(userId);
    }
    // 获取看单某个item的详情
    @GetMapping("/getKankanItemInfo")
    public Map<String, Object> getKankanItemInfo(String itemId){
        return kankanService.loadkankanItem(itemId);
    }

    // 点赞
    @PostMapping("/updateLike")
    public boolean optLikeForItem(String userId, String itemId){
        return kankanService.updateLikeForItem(userId, itemId);
    }
    @GetMapping("/testKankan")
    public String TestKankan(){
        return "hello, I'm kankan";
    }


}
