//package com.pet.commerce.core.thread;
//
//import com.pet.commerce.core.utils.SpringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.orm.jpa.EntityManagerFactoryUtils;
//import org.springframework.orm.jpa.EntityManagerHolder;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//
///**
// * 为新的线程打开hibernate session, 解决新线程no session问题
// *
// * @author hary
// * @since 1.0
// **/
//@Slf4j
//public abstract class OpenSessionThread extends Thread {
//
//    @Override
//    public void run() {
//        // 绑定session到当前线程
//        EntityManagerFactory emf = getEntityManagerFactory();
//        EntityManager em = emf.createEntityManager();
//        EntityManagerHolder emHolder = new EntityManagerHolder(em);
//        TransactionSynchronizationManager.bindResource(emf, emHolder);
//        log.debug("start session in new thread: {}", Thread.currentThread().getName());
//        try {
//            execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(emf);
//            EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
//            log.debug(this.getClass() + "  finish close session!");
//        }
//    }
//
//    /**
//     * 具体子类实现的业务方法，代替原来的线程的run方法
//     */
//    public abstract void execute();
//
//    private EntityManagerFactory getEntityManagerFactory() {
//        return SpringUtil.getBean(EntityManagerFactory.class);
//    }
//
//}
