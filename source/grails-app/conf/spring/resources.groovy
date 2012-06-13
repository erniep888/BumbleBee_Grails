import org.springframework.jdbc.datasource.DriverManagerDataSource
// Place your Spring DSL code here
beans = {
    dataSourceMantis = {
        driverClassName = "com.mysql.jdbc.Driver"
        url = "jdbc:mysql://test/mantis"
        username = "readonly"
        password = "readonly1"
    }

}
