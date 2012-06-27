dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            dbCreate = "update"
            url = "jdbc:mysql://seltec5test/bumblebee?useUnicode=yes&characterEncoding=UTF-8"
            username = "bumblebeeuser"
            password = "bumbl3"
        }

    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }
    production {
        dataSource {
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            dbCreate = "update"
            url = "jdbc:mysql://seltec5test/bumblebee?useUnicode=yes&characterEncoding=UTF-8"
            username = "bumblebeeuser"
            password = "bumbl3"
        }
//        dataSource {
//            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE"
//            pooled = true
//            properties {
//               maxActive = -1
//               minEvictableIdleTimeMillis=1800000
//               timeBetweenEvictionRunsMillis=1800000
//               numTestsPerEvictionRun=3
//               testOnBorrow=true
//               testWhileIdle=true
//               testOnReturn=true
//               validationQuery="SELECT 1"
//            }
//        }
    }
}
