// package com.mnan2c.fms.repository.base;
//
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.support.JpaEntityInformation;
// import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
// import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
// import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
// import org.springframework.data.repository.core.RepositoryInformation;
// import org.springframework.data.repository.core.RepositoryMetadata;
// import org.springframework.data.repository.core.support.RepositoryFactorySupport;
// import org.springframework.util.Assert;
//
// import javax.persistence.EntityManager;
// import java.io.Serializable;
//
// public class BaseRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends
// Serializable>
//    extends JpaRepositoryFactoryBean<T, S, ID> {
//  public BaseRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
//    super(repositoryInterface);
//  }
//
//  @Override
//  protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
//    return new BaseRepositoryFactory(em);
//  }
//  // 用内部类完成工厂
//  private static class BaseRepositoryFactory<T, I extends Serializable>
//      extends JpaRepositoryFactory {
//
//    private final EntityManager em;
//
//    public BaseRepositoryFactory(EntityManager em) {
//      super(em);
//      this.em = em;
//    }
//
//    // 设置=实现类是BaseRepositoryImpl
//    @Override
//    protected JpaRepositoryImplementation<?, ?> getTargetRepository(
//        RepositoryInformation information, EntityManager entityManager) {
//      JpaEntityInformation<?, Serializable> entityInformation =
//          this.getEntityInformation(information.getDomainType());
//      Object repository =
//          this.getTargetRepositoryViaReflection(
//              information, new Object[] {entityInformation, entityManager});
//      Assert.isInstanceOf(BaseRepositoryImpl.class, repository);
//      return (JpaRepositoryImplementation) repository;
//    }
//
//    // 设置自定义实现类class
//    @Override
//    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//      return BaseRepositoryImpl.class;
//    }
//  }
// }
