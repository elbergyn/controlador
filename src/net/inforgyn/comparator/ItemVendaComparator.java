package net.inforgyn.comparator;

import java.util.Comparator;

import net.inforgyn.model.ItemVenda;

public class ItemVendaComparator implements Comparator<ItemVenda>{

	@Override
	public int compare(ItemVenda item1, ItemVenda item2) {
		if(item1.getProduto() != null && item2.getProduto() != null){
			return item1.getProduto().getDescricao().compareTo(item2.getProduto().getDescricao());
		}else{
			return item1.getId().compareTo(item2.getId());
		}
	}

}
