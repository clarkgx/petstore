package com.pyxis.petstore.controller;

import com.pyxis.petstore.domain.product.Item;
import com.pyxis.petstore.domain.product.ItemInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/products/{productNumber}")
public class ItemsController {

	private final ItemInventory itemInventory;

    @Autowired
	public ItemsController(ItemInventory itemInventory) {
		this.itemInventory = itemInventory;
	}

    @RequestMapping( value = "/items", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("productNumber") String productNumber) {
        List<Item> items = itemInventory.findByProductNumber(productNumber);
        return new ModelAndView("items").addObject(items);
    }
}
