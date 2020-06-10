package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends ZuulFilter {

    /**
     *  filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     pre：路由之前
     routing：路由之时
     post： 路由之后
     error：发送错误调用
     * @return 返回其中一个状态
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     * @return 过滤的顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
     * @return  是否过滤
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     * 以下代码暂时是对请求中是否有token属性
     * @return 任意对象
     * @throws ZuulException 异常信息
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext cxt=RequestContext.getCurrentContext();
        HttpServletRequest request=cxt.getRequest();
        Object asstoken=request.getParameter("token");
        if (asstoken==null){
            cxt.setSendZuulResponse(false);
            cxt.setResponseStatusCode(401);
            try {

                cxt.getResponse().getWriter().write("token is error");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return cxt;
        }
        else {
            return null;
        }
    }
}
