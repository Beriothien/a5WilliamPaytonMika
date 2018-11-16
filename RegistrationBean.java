package com.gmail.paytongreenc;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

@Named(value = "registrationBean")
@SessionScoped

public class RegistrationBean implements Serializable {

    // Resource injection
    @Resource(name = "jdbc/homework")
    private DataSource ds;                      // Holds access to the database
    private List<UserDatabase> customerDatabase;    // Holds the customers in the database

    @PostConstruct
    public void init() {
        try {
            customerDatabase = loadUserDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<UserDatabase> getCustomerDatabase() {
        return customerDatabase;
    }

    public List<UserDatabase> loadCustomers() throws SQLException {
        if (ds == null) {
            throw new SQLException("Cannot get data source");
        }

        Connection conn = ds.getConnection();
        if (conn == null) {
            throw new SQLException("Cannot make data source connection");
        }

        List<UserDatabase> customerList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select ID, LAST_NAME, PHONE, EMAIL, POINTS from CUSTOMER");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                UserDatabase c = new UserDatabase();
                c.setId(result.getInt("ID"));
                c.setLastName(result.getString("LAST_NAME"));
                c.setPhone(result.getString("PHONE"));
                c.setEmail(result.getString("EMAIL"));
                
                customerList.add(c);
            }
        } finally {
            conn.close();
        }
        return customerList;
    }

    public String update(Integer id) throws SQLException {
        if (ds == null) {
            throw new SQLException("Cannot get data source");
        }

        Connection conn = ds.getConnection();
        if (conn == null) {
            throw new SQLException("Cannot make data source connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement("Update CUSTOMER set POINTS = POINTS + 1 where ID = ?");
            ps.setInt(1, id);                   // Set what the first ? stands for
            ps.executeUpdate();
        } finally {
            conn.close();
        }

        customerDatabase = loadCustomers();     // Reload the updated info
        return null;                            // Re-display index.xhtml
    }

    private int errors; // Keeps track of number of errors made

    //------------------------------------------------------------------------//
    // Do not use the constructor which is called by the system container
    // But use @PostConstruct method which will be called after the constructor
    //------------------------------------------------------------------------//   
    
    public void postConstruct() {
        errors = 0;
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Managed Bean Initialized", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    //------------------------------------------------------------------------//
    // Action controller
    //------------------------------------------------------------------------//
    public String showInfo() {
        // Check if the name, or gender, or city has not been filled.
        checkName();
        checkLastName();
        checkPassword();
        checkEmail();
        checkPhoneNumber();
        checkCity();
        checkGender();
        checkLanguages();

        // If name, gender, or city is not filled, take the user to incompleteinfo.xhtml
        // Else direct them to the page that shows their information
        if (errors > 0) {
            errors = 0;

            return "index";
        } else {
            return "showinformation";
        }
    }

    public String showDatabase() {
        checkName();
        checkLastName();
        checkPassword();
        checkEmail();
        checkPhoneNumber();
        checkCity();
        checkGender();
        checkLanguages();

        if (errors > 0) {
            errors = 0;

            return "index";
        } else {
            return "showdatabase";
        }
    }

    //------------------------------------------------------------------------//
    // Methods to check if input is missing
    //------------------------------------------------------------------------//
    public void checkName() {
        if (getname().trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fname", javaTextMsg);
            ++errors;
        } else if (!(getname().trim().matches("[a-zA-Z]*"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Name should only be letters", null);
            FacesContext.getCurrentInstance().addMessage("form1:fname", javaTextMsg);
            ++errors;
        }
    }

    public void checkLastName() {
        if (UserDatabase.getLastname.trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " Last Name missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:flastname", javaTextMsg);
            ++errors;
        } else if (!(getlastname().trim().matches("[a-zA-Z]*"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Last Name should only be letters", null);
            FacesContext.getCurrentInstance().addMessage("form1:flastname", javaTextMsg);
            ++errors;
        }
    }

    public void checkPassword() {
        if (getpassword().trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fpassword", javaTextMsg);
            ++errors;
        } else if (!(getpassword().trim().matches("[a-zA-Z0-9]*"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password should only be letters and numbers", null);
            FacesContext.getCurrentInstance().addMessage("form1:fpassword", javaTextMsg);
            ++errors;
        } else if (getpassword().length() < 4) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password should be more than 4 charcters", null);
            FacesContext.getCurrentInstance().addMessage("form1:fpassword", javaTextMsg);
            ++errors;
        }
    }

    public void checkEmail() {
        if (getemail().trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:femail", javaTextMsg);
            ++errors;
        } else if (!(email.trim().contains("@"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Email should contain letters and an @", null);
            FacesContext.getCurrentInstance().addMessage("form1:femail", javaTextMsg);
            ++errors;
        }
    }

    public void checkPhoneNumber() {
        if (getphoneNumber().trim().isEmpty()) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone Number missing", null);
            FacesContext.getCurrentInstance().addMessage("form1:fphoneNumber", javaTextMsg);
            ++errors;
        } else if (!(phoneNumber.trim().matches("^[2-9]\\d{2}-\\d{3}-\\d{4}$"))) {
            FacesMessage javaTextMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Phone number should be in xxx-xxx-xxxx format", null);
            FacesContext.getCurrentInstance().addMessage("form1:fphoneNumber", javaTextMsg);
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
