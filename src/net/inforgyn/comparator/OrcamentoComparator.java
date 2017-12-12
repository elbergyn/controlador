package net.inforgyn.comparator;

import java.util.Comparator;

import net.inforgyn.model.Orcamento;

public class OrcamentoComparator implements Comparator<Orcamento>{

	@Override
	public int compare(Orcamento orc1, Orcamento orc2) {
		return orc1.getId().compareTo(orc2.getId());
	}

}
