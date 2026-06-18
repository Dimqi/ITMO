package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entity.HitEntity;
import database.DatabaseManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;




@Named("hitHistoryBean")
// по условию(лучше SessionScoped)
@ApplicationScoped
public class HitHistoryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DatabaseManager databaseManager;
	
	@PostConstruct
	public void init() {
		setHits(databaseManager.getAllResults());
	}
	
	private List<HitEntity> hits = Collections.synchronizedList(new ArrayList<>());

    public List<HitEntity> getHits() {
        return Collections.unmodifiableList(hits);
    }
    
    public void setHits(List<HitEntity> hits) {
    	this.hits = hits;
    }
    
    public void addHit(HitEntity hitEntity) {
        hits.add(0, hitEntity);
    }

    public void clear() {
        hits.clear();
    }
    
    
}
