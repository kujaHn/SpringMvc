package hello.mvc.web.frontcontroller.v3.controller;

import hello.mvc.domain.member.Member;
import hello.mvc.domain.member.MemberRepository;
import hello.mvc.web.frontcontroller.ModelView;
import hello.mvc.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();

        ModelView mv = new ModelView("members");

        mv.getModel().put("members", members);

        return mv;
    }
}
