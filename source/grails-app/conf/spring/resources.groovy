import org.springframework.jdbc.datasource.DriverManagerDataSource
// Place your Spring DSL code here
beans = {
    dataSourceMantis = {
        driverClassName = "com.mysql.jdbc.Driver"
        url = "jdbc:mysql://ralappphp01t/mantis"
        username = "readonly"
        password = "r3ad0nly"
    }

}
