package hello.mvc.web.frontcontroller.v3.controller;

import hello.mvc.domain.member.Member;
import hello.mvc.domain.member.MemberRepository;
import hello.mvc.web.frontcontroller.ModelView;
import hello.mvc.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3{

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");

        mv.getModel().put("member", member);
        return mv;
    }
}
