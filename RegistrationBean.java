package com.gmail.william;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@Named(value = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {
    private String lastName;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String city;
    private String gender;
    private List<SelectItem> languages;

    private int errors; // Keeps track of number of errors made

    //------------------------------------------------------------------------//
    // Do not use the constructor which is called by the system container
    // But use @PostConstruct method which will be called after the constructor
    //------------------------------------------------------------------------//   
    @PostConstruct
    public void postConstruct() {
        errors = 0;
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Managed Bean Initialized", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
                                                              
    //------------------------------------------------------------------------//
    // Get and set
    //------------------------------------------------------------------------//
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public List<SelectItem> getLanguages() {
        return languages;
    }

    public void setLanguages(List<SelectItem> languages) {
        this.languages = languages;
    }

    //------------------------------------------------------------------------//
    // Action controller
    //------------------------------------------------------------------------//
    public String showInfo() {
        // Check if the name, or gender, or city has not been filled.
        checkLastName();
        checkName();
        checkPassword();
        checkEmail();
        checkPhone();
        checkCity();
        checkGender();
        checkLanguages();

        // If name, gender, or city is not filled, take the user to incompleteinfo.xhtml
        // Else direct them to the page that shows their information
        if (errors > 0) {
            errors = 0;

            //----------------------------------------------------------------//
            // You can get rid of the two statements below for the assignment
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            // You can get rid of the two statements above for the assignment
            //----------------------------------------------------------------//

            return "index";
        } else {
            return "showinformation";
        }
    }

    //------------------------------------------------------------------------//
    // Methods to check if input is missing
    //------------------------------------------------------------------------//
    public void checkName() {
        if (name.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fname", javaTextMsg);
            ++errors;
        } else if (!(name.trim().matches("[a-zA-Z]*"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Name should only be letters", null);
            FacesContext.getCurrentInstance().addMessage("form1:fname", javaTextMsg);
            ++errors;
        }
    }
    public void checkLastName() {
        if (lastName.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " Last Name missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:flname", javaTextMsg);
            ++errors;
        } else if (!(lastName.trim().matches("[a-zA-Z]*"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Name should only be letters", null);
            FacesContext.getCurrentInstance().addMessage("form1:flname", javaTextMsg);
            ++errors;
        }
    }
    public void checkPassword() {
        if (password.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "password missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fpass", javaTextMsg);
            ++errors;
        } 
        
        else if ((password.trim().length() <= 3)) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "invalid password", null);
            FacesContext.getCurrentInstance().addMessage("form1:fpass", javaTextMsg);
            ++errors;
        }
    }
    public void checkEmail() {
        if (email.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "email missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:femail", javaTextMsg);
            ++errors;
        }
        else if (!(email.trim().contains("@"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "invalid email", null);
            FacesContext.getCurrentInstance().addMessage("form1:femail", javaTextMsg);
            ++errors;
        }
    }
    public void checkPhone() {
        if (phone.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fphone", javaTextMsg);
            ++errors;
        } 
        else if (!(phone.trim().matches("^[2-9]\\d{2}-\\d{3}-\\d{4}$"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "invalid number", null);
            FacesContext.getCurrentInstance().addMessage("form1:fphone", javaTextMsg);
            ++errors;
        }
    }
    public void checkCity() {
        if (city.equals("-- choose --")) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "City not selected", null);
            FacesContext.getCurrentInstance().addMessage("form1:fcity", javaTextMsg);
            ++errors;
        }
    }

    public void checkGender() {
        if (gender == null) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a gender", null);
            FacesContext.getCurrentInstance().addMessage("form1:fgender", javaTextMsg);
            ++errors;
        }
    }

    public void checkLanguages() {
        if (languages.isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No language selected", null);
            FacesContext.getCurrentInstance().addMessage("form1:flanguage", javaTextMsg);
            ++errors;
        }
    }
}
