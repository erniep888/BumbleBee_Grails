import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.cache.ehcache.EhCacheFactoryBean
// Place your Spring DSL code here
beans = {
    dataSourceMantis(DriverManagerDataSource) {
        driverClassName = "com.mysql.jdbc.Driver"
        url = "jdbc:mysql://ralappphp01t/mantis"
        username = "readonly"
        password = "r3ad0nly"
    }

}
