package com.shrucode.user_service.Utils;


import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

//IMP QUES
//HOW TO MAKE CONFIGURATION PROPERTIES CLASS IMMUTABLE (ONCE DEFINED CANNOT BE CHANGED)

//to make immutable config class means which cant be changed
//1. remove @Component (bcoz no default constructor and spring ioc dont know what value to pass in constructor parameter)
//2. make fields final
//3. remove setter method
//4. add parameterized constructor so at time of creation of new object these values of field are set no setters as field are final hence immutable
//5. add annotation @ConfigurationPropertiesScan on top of main class -> giving responsibility to @ConfigurationProperties to create a new object of class immutableUserConfiguration as there is no @Component
//bean creation responsibility to Configuration binder it invoke our constructor with proper preoperty values as there is no setter method in immutable class
@ConfigurationProperties(prefix = "app.user")
@Validated
public class ImmutableUserConfiguration {
    private final String name;

    @Min(1) // same way of writing validation in Immutable and mutable user configuration
    private final int age;
    private final boolean active;
    ImmutableUserConfiguration(String name , int age, boolean active){
        this.name=name;
        this.age=age;
        this.active=active;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }
}
