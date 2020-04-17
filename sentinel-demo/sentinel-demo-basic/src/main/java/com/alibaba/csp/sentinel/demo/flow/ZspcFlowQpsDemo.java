package com.alibaba.csp.sentinel.demo.flow;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * @author : zhuansun
 * @date : 2020-04-17 14:54
 **/
public class ZspcFlowQpsDemo {

    private static final String RESOURCE_NAME = "abc";

    public static void main(String[] args) {

        initRules();
        Entry entry = null;
        try {
            //申请资源
            entry = SphU.entry(RESOURCE_NAME);
            test();
        } catch (BlockException e) {
            //如果抛出了异常，说明这个请求被拦截了。。
            e.printStackTrace();
        }finally {
            if (entry != null){
                entry.exit();
            }
        }
    }

    //加载规则
    private static void initRules(){
        List<FlowRule> flowRuleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RESOURCE_NAME);
        rule.setCount(1);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        flowRuleList.add(rule);
        FlowRuleManager.loadRules(flowRuleList);
    }

    private static void test(){
        System.out.println("test");
    }

}
