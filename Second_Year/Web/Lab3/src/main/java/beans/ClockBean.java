package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ClockBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currentTime;
    private String currentDate;

    public void updateTime() {
        Date now = new Date();
        
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss", locale);
        currentTime = sdfTime.format(now);

        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, d MMMM yyyy", locale);
        currentDate = sdfDate.format(now);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}

