package com.hyf.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;

import java.util.concurrent.Callable;

/**
 * Abstract test case enabling Shiro in test environments.
 *
 * @author baB_hyf
 * @date 2021/04/18
 * @see Subject#associateWith(Callable)
 */
public abstract class AbstractShiroTest {

    private static ThreadState subjectThreadState;

    protected static SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

    protected static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);
    }

    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * Allows subclasses to set the currently executing {@link Subject} instance.
     *
     * @param subject the Subject instance
     */
    protected static void setSubject(Subject subject) {
        clearSubject();
        subjectThreadState = new SubjectThreadState(subject);
        subjectThreadState.bind();
    }

    protected static void close() {
        clearSubject();
        clearSecurityManager();
    }

    /**
     * Clears Shiro's thread state, ensuring the thread remains clean for future test execution.
     */
    private static void clearSubject() {
        if (subjectThreadState != null) {
            subjectThreadState.clear();
            subjectThreadState = null;
        }
    }

    private static void clearSecurityManager() {
        try {
            SecurityManager securityManager = getSecurityManager();
            LifecycleUtils.destroy(securityManager);
        } catch (UnavailableSecurityManagerException e) {
            //we don't care about this when cleaning up the test environment
            //(for example, maybe the subclass is a unit test and it didn't
            // need a SecurityManager instance because it was using only
            // mock Subject instances)
        }
        setSecurityManager(null);
    }
}
