package servletContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HitHistoryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private List<ResultBean> hits = new ArrayList<>();

    public List<ResultBean> getHits() {
        return hits;
    }

    public void addHit(ResultBean result) {
        hits.add(0, result);
    }

    public void clear() {
        hits.clear();
    }
}
