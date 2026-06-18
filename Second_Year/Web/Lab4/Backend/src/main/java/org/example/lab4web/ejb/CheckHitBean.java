package org.example.lab4web.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.lab4web.database.ResultBean;
import org.example.lab4web.database.UserBean;
import org.example.lab4web.entity.ResultEntity;
import org.example.lab4web.entity.UserEntity;
import org.example.lab4web.restApi.CheckHitController;
import org.example.lab4web.utils.CheckHit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CheckHitBean {

    @Inject
    private ResultBean resultBean;

    @Inject
    private UserBean userBean;

    public boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r, String username){

        UserEntity user = userBean.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean hit = CheckHit.checkHit(x, y, r);
        ResultEntity result = new ResultEntity();
        result.setX(x);
        result.setY(y);
        result.setR(r);
        result.setHit(hit);
        result.setUser(user);
        resultBean.addResult(result);

        return hit;
    }

    public List<CheckHitController.CheckHitResponse> getUserResults(String username) {
        UserEntity user = userBean.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return resultBean.findByUser(user).stream()
                .map(r -> new CheckHitController.CheckHitResponse(
                        r.getX(), r.getY(), r.getR(), r.isHit()
                ))
                .collect(Collectors.toList());
    }


}
