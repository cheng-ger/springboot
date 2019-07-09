package com.cyl.it.practice.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author chengyuanliang
 * @desc  解决乱码问题
 * @since 2019-07-08
 */
@Configuration

public class ActivitiConfig /*implements ProcessEngineConfigurationConfigurer*/ {

    /*@Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        //这段代码表示是否使用activiti自带用户组织表，如果是，这里为true，
        // 如果不是，这里为false。由于本项目使用了视图的方式代替了原有的用户组织表，所以这里设置为false，
        // 这样启动就不用去检查用户组织表是否存在。
        processEngineConfiguration.setDbIdentityUsed(false);
        //这段代码表示启动的时候是否去创建表，如果第一次启动这里必须设置为true
        processEngineConfiguration.setDatabaseSchemaUpdate("false");


    }*/

    /*@Bean
    public ProcessEngine processEngine(DataSourceTransactionManager transactionManager, DataSource dataSource) throws IOException {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        //自动部署已有的流程文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes/*.bpmn");
        configuration.setTransactionManager(transactionManager);
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate("true");
        configuration.setDeploymentResources(resources);
        configuration.setDbIdentityUsed(false);
        return configuration.buildProcessEngine();
    }*/

   /* @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }*/

}
