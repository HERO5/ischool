package com.mrl.ischool.controller;

import com.mrl.ischool.common.dto.AjaxResult;
import com.mrl.ischool.knowledge.dto.KmJson;
import com.mrl.ischool.knowledge.entity.Knowledge;
import com.mrl.ischool.knowledge.entity.Progress;
import com.mrl.ischool.knowledge.service.KnowledgeService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/knowledge/api"})
public class KnowledgeController
{
    @Autowired
    private KnowledgeService knowledgeService;

    @RequestMapping({"/get"})
    @ResponseBody
    public AjaxResult getKnowledge() {
        AjaxResult ajaxResult = new AjaxResult();
        List<Knowledge> ks = this.knowledgeService.findAll();
        KmJson kmJson = KmJson.getJson(ks, null);
        ajaxResult.setData(kmJson);
        ajaxResult.setMsg("get knowledge finish");
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    @RequestMapping({"/getByStuId"})
    @ResponseBody
    public AjaxResult getByStuId(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        AjaxResult ajaxResult = new AjaxResult();
        String stuid = request.getParameter("stuid");
        List<Knowledge> ks = this.knowledgeService.findByStuId(stuid);
        List<Progress> ps = this.knowledgeService.getProgress(stuid);
        KmJson kmJson = KmJson.getJson(ks, ps);
        ajaxResult.setData(kmJson);
        ajaxResult.setMsg("get knowledge finish");
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
