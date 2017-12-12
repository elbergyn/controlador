package net.inforgyn.dataModel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import net.inforgyn.model.BaixaPagamento;

public class BaixaDataModel extends ListDataModel<BaixaPagamento> implements SelectableDataModel<BaixaPagamento>{

	public BaixaDataModel(List<BaixaPagamento> list) {
		super(list);
	}

	@Override
	public BaixaPagamento getRowData(String rowKey) {
		BaixaPagamento baixa = (BaixaPagamento) ((SelectableDataModel) getWrappedData()).getRowData(rowKey);
        return baixa;
	}

	@Override
	public Object getRowKey(BaixaPagamento baixa) {
		return baixa.getDebito().getId();
	}

}
