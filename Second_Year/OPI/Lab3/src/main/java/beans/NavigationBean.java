package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationBean {

    public String goToMain() {
        return "goToMain";  
    }

    public String goToIndex() {
        return "goToIndex";
    }
}