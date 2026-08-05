package com.shrucode.user_service.Utils;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;


//when we write @Component an empty bean is created then configuration binder invokes the setter method to set name age active from app.prop file
//to update address to create object of address config create default constructor with non static default constructor added takes reference of parent class but binder try to invoke no args constructor henc eif non static not work binder fails as with static -> not associated with parent class default constructor is no args constructor
//as in java -> NESTED CLASS -> NON STATIC METHOD SO DEF CONSTRUCTOR IS AddressConfig(Parent class) i.e we want args constructor hence use static
@Component
@ConfigurationProperties(prefix = "app.user") // maps configuraion from application.properties into Java object , bcoz @Value has too much duplication and no way to validate
@Validated // means all the below annotation in this class has to be validated if any validation fails application will fail to start
public class UserConfiguration {
    //try to keep same name as in app.prop file , even though camel case is allowed
    @NotBlank(message = "name must not be empty")
    private String name;
    @Min(value=1, message="Age cannot be 0")
    private int age;
    private boolean active;

    private AddressConfig address;

    private List<String> roles;
    private List<Course> course;
    private Map<String,String> preferences;
    private Map<String,AddressConfig> locations; //map of an object

    public Map<String, AddressConfig> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, AddressConfig> locations) {
        this.locations = locations;
    }


    public Map<String, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, String> preferences) {
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AddressConfig getAddress() {
        return address;
    }

    public void setAddress(AddressConfig address) {
        this.address = address;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    //Address as object and city,country are field in it

    public static class  AddressConfig{
        private String city;
        private String country;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Course{
         boolean enrolled;
         String name;

        public boolean isEnrolled() {
            return enrolled;
        }

        public void setEnrolled(boolean enrolled) {
            this.enrolled = enrolled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}
