package com.springinaction;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppDispatcherServlet
        //换成web.xml来配置DispatcherServlet和ContextLoaderListener
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    //将dispatcher servlet映射到/
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
