package bumblebee

import grails.converters.JSON
import java.text.SimpleDateFormat

class CacheService {

    boolean transactional = true

    boolean valid = false
    Closure lookup
    Date lastUpdate
    HashMap<String, Object> cache = new HashMap<String, Object>()

    def get(String name) {
        if (cache[name] == null || !valid){
            def result = lookup.call()
            cache[name] = result
            valid = true
            lastUpdate = new Date()
        }

        return cache[name]
    }

    def invalidate(){
        valid = false
    }

}
