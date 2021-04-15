package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송 기능
 *  http://localhost:8080/request-param?username=hello&age=20
 *  */

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RequestParamServlet.service");

        // 전체 파라미터 조회
        System.out.println("=======전체 파라미터 조회 START=======");
//        Enumeration<String> parameterNames = request.getParameterNames();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));
        System.out.println("=======전체 파라미터 조회 END=========");
        System.out.println();




        System.out.println("=======단일 파라미터 조회 START=======");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        System.out.println("username = " + username);
        System.out.println("age = " + age);

        System.out.println("=======단일 파라미터 조회 END=========");




        /** 이름이 같은경우 일반적으로는 앞서있는 파라미터를 가져온다
         *  하지만 getParameterValues 를 통해서 이름이 같은 복수의 파라미터들도 모두 가져올 수 있다.
         *  */
        System.out.println("=======이름이 같은 복수 파라미터 조회 START=======");
        String[] usernames = request.getParameterValues("username");

        for (String name : usernames) {
            System.out.println("username = " + name);
        }

        System.out.println("=======이름이 같은 복수 파라미터 조회 END=========");


        response.getWriter().write("OK");
    }
}
