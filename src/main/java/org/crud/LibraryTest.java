package org.crud;

import org.library.ApplicationConfiguration;
import org.library.StartupClass;

@StartupClass(controllersPackages = {"com.controllers"}, servicesPackages = {"com.services"}, repositoriesPackages = {"org.persistence"})
public class LibraryTest {

    public static void main(String[] args) {
        try{
            ApplicationConfiguration.load(LibraryTest.class, args);
        }
        catch(Exception ex) {
         ex.printStackTrace();
        }
    }
}
