package bumblebee.viewmodel

import org.springframework.web.multipart.MultipartFile
import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: pascherk
 * Date: 6/14/12
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class ArtifactUploadViewModel {
    Integer id
    MultipartFile contents

    static constraints = {
        id(nullable: false)
        contents(nullable: false)
    }
}
