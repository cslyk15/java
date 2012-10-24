package org.agoncal.application.petstore.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.parsers.ParserConfigurationException;

import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.service.CatalogService;
import org.agoncal.application.petstore.service.DBPopulatorTomcat;
import org.agoncal.application.petstore.util.Loggable;
import org.xml.sax.SAXException;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Named
//@RequestScoped TODO should be request scoped
@SessionScoped
@Loggable
@CatchException
public class CatalogController extends Controller implements Serializable {

	
    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private CatalogService catalogService;
    
    @Inject
    private transient DBPopulatorTomcat populatorDb;

    private String categoryName;
    private Long productId;
    private Long itemId;

    private String keyword;
    private Product product;
    private Item item;
    private List<Product> products;
    private List<Item> items;

    // ======================================
    // =              Public Methods        =
    // ======================================

    public String doFindProducts() {
        products = catalogService.findProducts(categoryName);
        return "showproducts.faces";
    }

    public String doFindItems() {
        product = catalogService.findProduct(productId);
        items = catalogService.findItems(productId);
        return "showitems.faces";
    }

    public String doFindItem() {
        item = catalogService.findItem(itemId);
        return "showitem.faces";
    }

    public String doSearch() {
        items = catalogService.searchItems(keyword);
        return "searchresult.faces?keyword=" + keyword + "&faces-redirect=true";
    }

    public String populateDb() {   		
    		try {
    			populatorDb.populateDb();
    		} catch (ParserConfigurationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SAXException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ConstraintViolationException e) {
    			for(ConstraintViolation constraintViolation : e.getConstraintViolations()) {
    				System.out.println(constraintViolation.getMessage());
    			}
    		}
    	return "main.faces";
    }
    
    // ======================================
    // =         Getters & setters          =
    // ======================================

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}