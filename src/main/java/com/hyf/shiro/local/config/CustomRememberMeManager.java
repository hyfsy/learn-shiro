package com.hyf.shiro.local.config;

import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomRememberMeManager extends AbstractRememberMeManager {

    private String name;

    @Override
    protected void forgetIdentity(Subject subject) {
        System.out.println("--> CustomRememberMeManager --> forgetIdentity(Subject)");
    }

    @Override
    public void forgetIdentity(SubjectContext subjectContext) {
        System.out.println("--> CustomRememberMeManager --> forgetIdentity(SubjectContext)");
    }

    @Override
    protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {
        System.out.println("--> CustomRememberMeManager --> rememberSerializedIdentity");
    }

    @Override
    protected byte[] getRememberedSerializedIdentity(SubjectContext subjectContext) {
        System.out.println("--> CustomRememberMeManager --> getRememberedSerializedIdentity");
        return new byte[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
