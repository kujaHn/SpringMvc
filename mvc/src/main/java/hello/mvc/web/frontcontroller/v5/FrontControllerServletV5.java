package hello.mvc.web.frontcontroller.v5;

import hello.mvc.web.frontcontroller.ModelView;
import hello.mvc.web.frontcontroller.MyView;
import hello.mvc.web.frontcontroller.v3.ControllerV3;
import hello.mvc.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.mvc.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.mvc.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.mvc.web.frontcontroller.v4.ControllerV4;
import hello.mvc.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.mvc.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.mvc.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.mvc.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.mvc.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //  기존의 private Map<String, ControllerV4> controllerMap = new HashMap<>();과 달리 모든 버전이 다 들어가기때문에 Object로 정의
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 핸들러 호출
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 핸들러를 던지며 일치하는 핸들러 어뎁터 찾아오기
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 핸들 호출 -> 실제 컨트롤러 호출 -> ModelView 반환
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.rendering(mv.getModel(), request, response);
    }


    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler){
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        } throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
