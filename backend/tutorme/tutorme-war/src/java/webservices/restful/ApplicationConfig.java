/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import java.util.Set;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author Owen Tay
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(filter.CORSFilter.class);
        resources.add(filter.JWTTokenNeededFilter.class);
        resources.add(webservices.restful.ChatResource.class
        resources.add(webservices.restful.JobListingResource.class
        resources.add(webservices.restful.MessageResource.class);
        resources.add(webservices.restful.OfferResource.class);
        resources.add(webservices.restful.PersonResource.class);
        resources.add(webservices.restful.SubjectResource.class);
        resources.add(webservices.restful.TutorResource.class);
 }
    
}