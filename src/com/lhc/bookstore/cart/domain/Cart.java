package com.lhc.bookstore.cart.domain;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 购物车
 * @author lhc
 *
 */
public class Cart {
	/**
	 * 方便操作某一个条目：用map
	 * key:bid value:CartItem
	 * 有顺序：LinkedHashMap
	 */
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>(); 
	/**
	 * 购物车总价=商品条目小计之和
	 * 处理二进制误差
	 * @return
	 */
	public double getTotal() {
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : map.values()) {
			BigDecimal subTotal = new BigDecimal(cartItem.getSubtotal() + "");
			total = total.add(subTotal);
		}
		return total.doubleValue();
	}
	/**
	 * 添加购物车
	 */
	public void add(CartItem cartItem) {
		if(map.containsKey(cartItem.getBook().getBid())) {
			CartItem _cartItem = map.get(cartItem.getBook().getBid());
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
			map.put(_cartItem.getBook().getBid(), _cartItem);
		}else{
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	/**
	 * 清空购物车
	 */
	public void clear() {
		map.clear();
	}
	/**
	 * 删除某一条目
	 */
	public void delete(String bid) {
		map.remove(bid);
	}
	/**
	 * 获取所有条目
	 * @return
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}
