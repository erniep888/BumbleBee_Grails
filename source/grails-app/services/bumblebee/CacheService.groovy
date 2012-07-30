package bumblebee

import grails.converters.JSON
import java.text.SimpleDateFormat
import java.lang.reflect.Method

class CacheService {

    boolean transactional = true
    static int timeToLiveInSeconds = 180

    HashMap<String, Date> lastUpdatePerEntry = new HashMap<String, Date>()
    HashMap<String, Object> cache = new HashMap<String, Object>()

    def get(String name) {
        return cache[name]
    }

    def set(String name, Object value){
        Calendar calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, timeToLiveInSeconds)
        cache[name] = value
        lastUpdatePerEntry[name] =  calendar.time
    }

    def isValid(String name){
        def isValid = false
        def now = new Date()
        if (lastUpdatePerEntry &&
            lastUpdatePerEntry[name] &&
            !now.after(lastUpdatePerEntry[name]))  {
            isValid = true
        }
        return isValid
    }

    def invalidate(String name){
        if (lastUpdatePerEntry &&
            lastUpdatePerEntry[name] ){
            lastUpdatePerEntry[name] = new Date()
        }
    }
}
