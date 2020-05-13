/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appload.client.usedby.server.app.xbl.admin.manager;

import org.apache.log4j.Logger;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import com.appload.client.usedby.server.app.xbl.admin.manager.dao.CouponDAO;
import com.appload.client.usedby.server.app.xbl.admin.manager.dao.ProductDAO;
import com.appload.client.usedby.server.app.xbl.manager.exception.CouponInvalidParametersException;
import com.appload.client.usedby.server.app.xbl.manager.exception.CouponNotFoundException;
import org.json.me.JSONArray;

/**
 *
 */
public class AdminCouponManager {
    /* GIT TEST */
    //#######################################
    // Class variables
    //#######################################
    private final static Logger LOGGER = Logger.getLogger(AdminCouponManager.class);
    //
    //#######################################
    // Class methods
    //#######################################
    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    public JSONObject getCouponList(JSONObject params) throws Exception {
        CouponDAO coupondao = null;
        JSONObject jsonCoupons = new JSONObject();
        try {
            coupondao = new CouponDAO();
            jsonCoupons = coupondao.getCouponList(params);
            if (jsonCoupons == null) {
                throw new CouponNotFoundException();
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return jsonCoupons;
    }

    /**
     *
     * @param coupon_id
     * @return
     * @throws Exception
     */
    public JSONObject getCoupon(Long coupon_id) throws Exception {
        CouponDAO coupondao = null;
        JSONObject jsonCoupon = null;
        try {
            if (coupon_id == null) {
                throw new CouponInvalidParametersException();
            }
            coupondao = new CouponDAO();
            jsonCoupon = coupondao.getCouponById(coupon_id);
            if (jsonCoupon == null) {
                throw new CouponNotFoundException();
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return jsonCoupon;
    }
    
    /**
     * 
     * @param code
     * @return
     * @throws Exception 
     */
    public JSONObject getCouponByCode(String code) throws Exception {
        CouponDAO coupondao = null;
        JSONObject jsonCoupon = null;
        try {
            if (code == null) {
                throw new CouponInvalidParametersException();
            }
            coupondao = new CouponDAO();
            jsonCoupon = coupondao.getCouponByCode(code);
            if (jsonCoupon == null) {
                throw new CouponNotFoundException();
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return jsonCoupon;
    }

    /**
     *
     * @param jsonCoupon
     * @return
     * @throws CouponInvalidParametersException
     * @throws CouponNotFoundException
     * @throws JSONException
     * @throws Exception
     */
    public boolean addCoupon(JSONObject jsonCoupon) throws
            CouponInvalidParametersException, CouponNotFoundException, JSONException, Exception {
        CouponDAO coupondao = null;
        boolean addResult = false;
        try {
            if (jsonCoupon.getString("code") == null || jsonCoupon.getString("type") == null || jsonCoupon.getInt("amount") <= 0) {
                throw new CouponInvalidParametersException();
            }
            coupondao = new CouponDAO();
            Long newCouponId = coupondao.createCoupon(jsonCoupon);
            if (newCouponId <= 0) {
                throw new Exception("Coupon could not be created.");
            }// newCouponId > 0
            addResult = true;
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return addResult;
    }

    /**
     * 
     * @param jsonCoupon
     * @return
     * @throws CouponInvalidParametersException
     * @throws CouponNotFoundException
     * @throws JSONException
     * @throws Exception 
     */
    public boolean editCoupon(JSONObject jsonCoupon) throws
            CouponInvalidParametersException, CouponNotFoundException, JSONException, Exception {
        CouponDAO coupondao = null;
        boolean editResult = false;
        try {
            if (jsonCoupon.getLong("coupon_id") <= 0 || jsonCoupon.getString("code") == null || jsonCoupon.getString("type") == null || jsonCoupon.getInt("amount") <= 0) {
                throw new CouponInvalidParametersException();
            }
            coupondao = new CouponDAO();
            Long update = coupondao.updateCoupon(jsonCoupon);
            if (update <= 0) {
                throw new Exception("Coupon could not be updated.");
            }// update > 0
            editResult = true;
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return editResult;
    }

    /**
     *
     * @param ids
     * @param enabled
     * @return
     * @throws CouponInvalidParametersException
     * @throws JSONException
     * @throws Exception
     */
    public int enableCoupons(JSONArray ids, boolean enabled) throws
            CouponInvalidParametersException, JSONException, Exception {
        CouponDAO coupondao = null;
        int editResult = -1;
        try {
            coupondao = new CouponDAO();
            editResult = coupondao.enableCoupons(ids, enabled);
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return editResult;
    }

    /**
     * 
     * @param ids
     * @return
     * @throws CouponInvalidParametersException
     * @throws JSONException
     * @throws Exception 
     */
    public int deleteCoupons(JSONArray ids) throws
            CouponInvalidParametersException, JSONException, Exception {
        CouponDAO coupondao = null;
        int deleteResult = -1;
        try {
            coupondao = new CouponDAO();
            deleteResult = coupondao.deleteCoupons(ids);
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        } finally {
            coupondao = null;
        }
        return deleteResult;
    }
    
    /**
     *
     * @return
     */
    public JSONObject countCoupons() {
        JSONObject jsonCount = null;
        CouponDAO coupondao = null;
        try {
            coupondao = new CouponDAO();
            jsonCount = coupondao.getCouponsCount();
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw ex;
        }
        return jsonCount;
    }
    
    /**
     * 
     * @param coupons
     * @param products
     * @return
     * @throws Exception 
     */
    public int calculateDiscount(JSONArray coupons, JSONArray products) throws Exception{
        ProductDAO prodDao = null;
        int discount_total = 0;
        try {
            if(coupons == null || products == null){
                throw new CouponInvalidParametersException();
            }
            if(coupons.length() <= 0 || products.length() <= 0){
                return 0;
            }
            for(int i=0; i<coupons.length(); i++){
                JSONObject coupon;
                Object aObj = coupons.get(i);
                if(!(aObj instanceof JSONObject)){
                    // if coupon is not object, check if id
                    Long tryId = Long.parseLong(coupons.getString(i));
                    coupon = this.getCoupon(tryId);
                    if(coupon == null){
                        throw new CouponNotFoundException();
                    }
                }
                else {
                    coupon = coupons.getJSONObject(i);
                }
                String type = coupon.getString("type");
                int amount = coupon.getInt("amount");
                
                JSONObject options = new JSONObject(coupon.getString("options"));
                JSONArray include = options.getJSONArray("includeProducts");
                JSONArray exclude = options.getJSONArray("excludeProducts");
                
                JSONArray _products = products;
                // Prepare concerning products
                if (type.equals("product_percent") || type.equals("product_set")) {
                    // Filtering only if discount is product-specific
                    if (include.length() > 0) {
                        // Filter out products not in list
                        JSONArray tmp = new JSONArray();
                        for (int a = 0; a < _products.length(); a++) {
                            JSONObject item = _products.getJSONObject(a);
                            prodDao = new ProductDAO();
                            JSONObject prod_tmp = prodDao.getProductById(item.getLong("product_id"));
                            for (int b = 0; b < include.length(); b++) {
                                if (prod_tmp.getLong("article_id") == include.getLong(b)) {
                                    tmp.put(item);
                                }
                            }
                        }
                        _products = tmp;
                    }
                    if (exclude.length() > 0) {
                        // Filter out products that are in list
                        JSONArray tmp = new JSONArray();
                        for (int a = 0; a < _products.length(); a++) {
                            JSONObject item = _products.getJSONObject(a);
                            prodDao = new ProductDAO();
                            JSONObject prod_tmp = prodDao.getProductById(item.getLong("product_id"));
                            for (int b = 0; b < exclude.length(); b++) {
                                boolean excluded = false;
                                if (prod_tmp.getLong("article_id") == include.getLong(b)) {
                                    excluded = true;
                                }
                                if (!excluded) {
                                    tmp.put(item);
                                }
                            }
                        }
                        _products = tmp;
                    }
                }
                // Discount products included
                if(_products.length() > 0){
                    for (int a = 0; a < _products.length(); a++) {
                        JSONObject item = _products.getJSONObject(a);
                        if (item.has("price")) {
                            if(type.equals("cart_percent") || type.equals("product_percent")){
                                double val = amount;
                                discount_total += ((item.getInt("price") * (val / 100)));
                            }
                            else if(type.equals("cart_set") || type.equals("product_set")){
                                discount_total += (item.getInt("price") - amount);
                            }
                        }
                    }
                }
                else {
                    LOGGER.error("No discounted products available.");
                }
            }
            return discount_total;
        } catch (CouponInvalidParametersException ex) {
            LOGGER.error(ex);
            throw ex;
        }
        finally {
            prodDao = null;
        }
    }
}
